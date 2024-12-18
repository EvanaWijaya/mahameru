import { v4 as uuidv4 } from 'uuid';
import { nanoid } from 'nanoid';

import { pool } from '../configs/dbConfig.js';
import validateTransactionRequest from '../validations/transactionValidations.js';

const getTicketsService = async (req) => {
  try {
    const [rows] = await pool.query('SELECT id, name, price, visitor_quantity, description FROM tickets');
    return rows;
  } catch (error) {
    throw new Error(error.message);
  }
};

const getTicketByIdService = async (req) => {
  try {
    const id = req.params.id;
    const [rows] = await pool.query('SELECT name, price, stock_quantity, visitor_quantity, visitor_quantity, description FROM tickets WHERE id = ?', [id]);
    if (rows.length === 0) {
      throw new Error('Tiket tidak ditemukan');
    }
    return rows[0];
  } catch (error) {
    throw new Error(error.message);
  }
}

const getInventoriesService = async (req) => {
  try {
    const [rows] = await pool.query('SELECT id, code, name, price FROM inventories');
    return rows;
  } catch (error) {
    throw new Error(error.message);
  }
}

const createTransactionService = async (req) => {
  try {
    // Validasi request
    await validateTransactionRequest(req, pool);
    const { date, tickets, inventories, visitors_id, total_price } = req.body;
    let visitorCapacity = 0;
    let checkTotalPrice = 0;
    // Validasi tiket
    for (const ticket of tickets) {
      const [ticketData] = await pool.query(
        `SELECT stock_quantity, visitor_quantity, price FROM tickets WHERE id = ?`,
        [ticket.id]
      );
      if (!ticketData.length) throw new Error(`Tiket dengan ID ${ticket.id} tidak ditemukan.`);

      const ticketStock = ticketData[0].stock_quantity;
      visitorCapacity += ticketData[0].visitor_quantity * ticket.amount;
      checkTotalPrice += ticketData[0].price * ticket.amount;

      const [ticketsSold] = await pool.query(
        `SELECT SUM(amount) AS sold FROM transaction_tickets 
          JOIN transactions ON transaction_tickets.transaction_id = transactions.id 
          WHERE transaction_tickets.ticket_id = ? AND transactions.date = ?`,
        [ticket.id, date]
      );
      const sold = ticketsSold[0]?.sold || 0;
      const availableStock = ticketStock - sold;

      if (availableStock < ticket.amount) {
        throw new Error(`Tiket dengan ID ${ticket.id} hanya tersisa ${availableStock} untuk tanggal ${date}.`);
      }
    }

    // Validasi jumlah visitor
    if (visitors_id.length > visitorCapacity) {
      throw new Error(
        `Jumlah visitor (${visitors_id.length}) melebihi kapasitas maksimum (${visitorCapacity}).`
      );
    }

    // Validasi inventaris (jika ada)
    if (inventories) {
      for (const inventory of inventories) {
        const [inventoryStock] = await pool.query(
          `SELECT stock_quantity, price FROM inventories WHERE id = ?`,
          [inventory.id]
        );
        if (!inventoryStock.length) throw new Error(`Inventory dengan ID ${inventory.id} tidak ditemukan.`);

        const stockQuantity = inventoryStock[0].stock_quantity;
        checkTotalPrice += inventoryStock[0].price * inventory.amount;

        const [inventoriesUsed] = await pool.query(
          `SELECT SUM(amount) AS used FROM transaction_inventories 
            JOIN transactions ON transaction_inventories.transaction_id = transactions.id 
            WHERE transaction_inventories.inventory_id = ? AND transactions.date = ?`,
          [inventory.id, date]
        );
        const used = inventoriesUsed[0]?.used || 0;
        const availableStock = stockQuantity - used;

        if (availableStock < inventory.amount) {
          throw new Error(`Inventory dengan ID ${inventory.id} hanya tersisa ${availableStock} untuk tanggal ${date}.`);
        }
      }
    }

    // Validasi total harga
    if (total_price !== checkTotalPrice) {
      throw new Error('Total harga tidak sesuai dengan jumlah tiket dan inventory yang dipilih.');
    }

    // Validasi visitor milik user
    for (const visitorId of visitors_id) {
      const [visitorRows] = await pool.query(
        'SELECT * FROM visitors WHERE id = ? AND user_id = ?',
        [visitorId, req.user_id]
      );
      if (!visitorRows.length) {
        throw new Error(`Visitor dengan ID ${visitorId} bukan milik user.`);
      }
    }

    // Menyimpan transaksi
    const transactionId = uuidv4();
    const userId = req.user_id;
    const transactionNumber = nanoid(7);
    const transactionCode = nanoid(8);
    const status = 'Berhasil';

    await pool.query(
      `INSERT INTO transactions(id, user_id, transaction_number, total_price, date, transaction_code, status) VALUES(?, ?, ?, ?, ?, ?, ?)`,
      [transactionId, userId, transactionNumber, total_price, date, transactionCode, status]
    );

    if (inventories) {
      for (const inventory of inventories) {
        await pool.query(
          `INSERT INTO transaction_inventories(transaction_id, inventory_id, amount) VALUES(?, ?, ?)`,
          [transactionId, inventory.id, inventory.amount]
        );
      }
    }

    for (const ticket of tickets) {
      await pool.query(
        `INSERT INTO transaction_tickets(transaction_id, ticket_id, amount) VALUES(?, ?, ?)`,
        [transactionId, ticket.id, ticket.amount]
      );
    }

    const [transactionTicketId] = await pool.query(
      `SELECT id FROM transaction_tickets WHERE transaction_id = ?`,
      [transactionId]
    );
    for (const visitorId of visitors_id) {
      await pool.query(
        `INSERT INTO transaction_visitors(transaction_ticket_id, visitor_id) VALUES(?, ?)`,
        [transactionTicketId[0].id, visitorId]
      );
    }

    // Menyiapkan response
    // Mendapatkan username dari user_id
    let [username] = await pool.query('SELECT username FROM users WHERE id = ?', [userId]);
    username = username[0].username;

    // Mendapatkan data visitor
    let visitors = [];
    for (const visitor_id of visitors_id) {
      const visitor = await pool.query(
        'SELECT id, name, nik FROM visitors WHERE id = ?',
        [visitor_id]
      );
      visitors.push({
        id: visitor[0][0].id,
        name: visitor[0][0].name,
        nik: visitor[0][0].nik
      });
    }

    const responses = {
      status: status,
      transaction_number: transactionNumber,
      ketegori: 'Tiket Masuk',
      transaction_date: new Date().toISOString().split('T')[0],
      payment_method: 'Transfer Bank',
      total_price: total_price,
      username: username,
      visitor_amount: visitors.length,
      date: date,
      transaction_code: transactionCode,
      visitors
    }
    return responses;
  } catch (error) {
    throw new Error(error.message);
  }
};

const getTicketsHistoryService = async (req) => {
  try {
    const user_id = req.user_id;

    // Mendapatkan data transaksi
    const [transactionsId] = await pool.query('SELECT id FROM transactions WHERE user_id = ?', [user_id]);
    // Mendapatkan data id tiket dari transaksi
    let ticketsId = [];
    for (const transaction of transactionsId) {
      const [ticketId] = await pool.query('SELECT id, ticket_id FROM transaction_tickets WHERE transaction_id = ?', [transaction.id]);
      ticketsId.push(...ticketId);
    }
    // Mendapatkan data tiket dari id tiket
    let response = [];
    for (const ticketId of ticketsId) {
      const [ticket] = await pool.query('SELECT name, description, price FROM tickets WHERE id = ?', [ticketId.ticket_id]);
      ticket[0].transaction_ticket_id = ticketId.id;
      response.push(...ticket);
    }

    return response;
  } catch (error) {
    throw new Error(error.message);
  }
};

const createReviewRatingTicketService = async (req) => {
  try {
    const { transaction_ticket_id, review, rating } = req.body;
    if (!transaction_ticket_id || !review || !rating) {
      throw new Error('Semua field harus diisi.');
    }
    // Validasi rating
    if (rating < 1 || rating > 5) {
      throw new Error('Rating harus antara 1 sampai 5.');
    }

    const user_id = req.user_id;

    // Mendapatkan data transaksi user
    const [transactionsId] = await pool.query('SELECT id FROM transactions WHERE user_id = ?', [user_id]);
    // Mendapatkan data id tiket dari transaksi user
    let transactionTicketId = []
    for (const transactionid of transactionsId) {
      const ticketId = await pool.query('SELECT id FROM transaction_tickets WHERE transaction_id = ?', [transactionid.id]);
      transactionTicketId.push(...ticketId[0])
    }
    // Cek apakah transaction_ticket_id milik user
    let transactionTicketIdCheck = false
    for (const ticketId of transactionTicketId) {
      if (ticketId.id === transaction_ticket_id) {
        transactionTicketIdCheck = true
        break;
      } 
    }
    if (!transactionTicketIdCheck) {
      throw new Error('Tiket tidak ditemukan.');
    }

    // Simpan rating dan review
    await pool.query(
      'UPDATE transaction_tickets SET review = ?, rating = ? WHERE id = ?',
      [review, rating, transaction_ticket_id]
    );

    return { message: 'Rating dan review berhasil disimpan.' };
  } catch (error) {
    throw new Error(error.message);
  }
};

const getReviewRatingTicketService = async (req) => {
  try {
    const user_id = req.user_id;

    // Mendapatkan data transaksi user
    const [transactionsId] = await pool.query('SELECT id FROM transactions WHERE user_id = ?', [user_id]);
    // Mendapatkan data id tiket dari transaksi user
    let transactionTicketId = []
    for (const transactionid of transactionsId) {
      const ticketId = await pool.query('SELECT id FROM transaction_tickets WHERE transaction_id = ?', [transactionid.id]);
      transactionTicketId.push(...ticketId[0])
    }
    // Mendapatkan data rating dan review dari id tiket transaksi user
    let response = [];
    for (const ticketId of transactionTicketId) {
      const [review] = await pool.query('SELECT id, review, rating FROM transaction_tickets WHERE id = ?', [ticketId.id]);
      if (review[0].review && review[0].rating) {
        response.push(...review);
      }
    }

    // Merapikan response
    response = response.map(item => ({
      transaction_ticket_id: item.id,
      review: item.review,
      rating: item.rating
    }));

    return response;
  } catch (error) {
    throw new Error(error.message);
  }
}

export {
  getTicketsService,
  getTicketByIdService,
  getInventoriesService,
  createTransactionService,
  getTicketsHistoryService,
  createReviewRatingTicketService,
  getReviewRatingTicketService,
};