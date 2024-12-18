import { pool } from '../configs/dbConfig.js';

const getTransactionsTotalService = async (req) => {
  try {
    const [rows] = await pool.query(`SELECT COUNT(*) AS total FROM transactions`);

    return {
      transaction_total: rows[0].total
    };
  } catch (error) {
    throw new Error(error.message);
  }
}

const searchTransactionsService = async (req) => {
  try {
    const { query = '', page = 1 } = req.query;
    const limit = 4; // Jumlah data per halaman
    const offset = (page - 1) * limit; // Menghitung offset berdasarkan halaman

    // Menjalankan query pencarian transaksi dengan paginasi
    const [rows] = await pool.query(
      `SELECT 
        transactions.id AS transaction_id,
        transactions.date AS transaction_date, 
        users.username AS username, 
        tickets.name AS ticket_name,
        transactions.total_price AS ticket_price, 
        transactions.status AS transaction_status,
        transaction_tickets.amount AS ticket_quantity
      FROM 
        transactions
      JOIN 
        users ON transactions.user_id = users.id
      JOIN 
        transaction_tickets ON transactions.id = transaction_tickets.transaction_id
      JOIN 
        tickets ON transaction_tickets.ticket_id = tickets.id
      WHERE 
      (transactions.date LIKE ? OR users.username LIKE ? OR transactions.total_price LIKE ? OR tickets.name LIKE ? OR transactions.status LIKE ?)
      LIMIT ? OFFSET ?;`,
      [`%${query}%`, `%${query}%`, `%${query}%`, `%${query}%`, `%${query}%`, limit, offset]
    );

    // Mendapatkan total jumlah transaksi yang sesuai dengan query pencarian
    const [[{ total }]] = await pool.query(
      `SELECT 
        COUNT(*) AS total 
      FROM 
        transactions
      JOIN 
        users ON transactions.user_id = users.id
      JOIN 
        transaction_tickets ON transactions.id = transaction_tickets.transaction_id
      JOIN 
        tickets ON transaction_tickets.ticket_id = tickets.id
      WHERE 
        (transactions.date LIKE ? OR users.username LIKE ? OR transactions.total_price LIKE ? OR tickets.name LIKE ?);`,
      [`%${query}%`, `%${query}%`, `%${query}%`, `%${query}%`]
    );

    // Menghitung total halaman
    const totalPage = Math.ceil(total / limit); 

    return { rows, total, totalPage };
  } catch (error) {
    throw new Error(error.message);
  }
}

const getTransactionByIdService = async (req) => {
  try {
    const id = req.params.id;

    // get transaction data
    const [transaction] = await pool.query(
      `SELECT 
        transaction_number,
        date,
        payment_method,
        total_price,
        status,
        user_id
      FROM
        transactions
      WHERE
        id = ?
      `,
      [id]
    );

    // get ticket data
    const [tickets_id] = await pool.query(
      `SELECT ticket_id FROM transaction_tickets WHERE transaction_id = ?`,
      [id] 
    );
    let tiketsName = [];
    for (const ticket_id of tickets_id) {
      const [rows] = await pool.query(
        `SELECT name FROM tickets WHERE id = ?`,
        [ticket_id.ticket_id]
      );
      tiketsName.push(rows[0].name);
    }

    // get user data
    let [username] = await pool.query(
      `SELECT username FROM users WHERE id = ?`,
      [transaction[0].user_id]
    );

    // menyiapkan response
    const response = {
      transaction_number: transaction[0].transaction_number,
      username: username[0].username,
      date: transaction[0].date,
      payment_method: transaction[0].payment_method,
      total_price: transaction[0].total_price,
      status: transaction[0].status,
      tickets: tiketsName
    };

    return response;
  } catch (error) {
    throw new Error(error.message);
  }
}

const getTransactionsTotalPriceAmountService = async (req) => {
  try {
    const [rows] = await pool.query(`SELECT SUM(total_price) AS total FROM transactions`); 
    return { total_price_amount: rows[0].total };
  } catch (error) {
    throw new Error(error.message);
  }
}

const getTransactionsFilterService = async (req) => {
  try {
    const { query = '', page = 1 } = req.query;

    // Validasi query
    const validQueries = ['start_date', 'end_date', 'type', 'status'];
    if (query && !validQueries.includes(query)) {
      throw new Error('Query tidak valid. Silakan gunakan start_date, end_date, type, atau status.');
    }

    const limit = 4; // Jumlah data per halaman
    const offset = (page - 1) * limit; // Menghitung offset berdasarkan halaman

    // Menentukan kolom dan arah pengurutan untuk ORDER BY
    // Defaultnya adalah ORDER BY transactions.date ASC
    let orderByColumn = 'transactions.date';
    let orderDirection = 'ASC';
    if (query === 'end_date') {
      orderDirection = 'DESC';
    } else if (query === 'type') {
      orderByColumn = 'tickets.name';
    } else if (query === 'status') {
      orderByColumn = 'transactions.status';
    }

    // Query untuk mendapatkan data transaksi dengan paginasi
    const [rows] = await pool.query(`
      SELECT 
        transactions.id AS transaction_id,
        transactions.date AS transaction_date, 
        users.username AS username, 
        tickets.name AS ticket_name,
        transactions.total_price AS ticket_price, 
        transactions.status AS transaction_status,
        transaction_tickets.amount AS ticket_quantity
      FROM 
        transactions
      JOIN 
        users ON transactions.user_id = users.id
      JOIN 
        transaction_tickets ON transactions.id = transaction_tickets.transaction_id
      JOIN 
        tickets ON transaction_tickets.ticket_id = tickets.id
      ORDER BY ${orderByColumn} ${orderDirection}
      LIMIT ? OFFSET ?;
    `, [limit, offset]);

    // Query untuk menghitung total data
    const [[{ total }]] = await pool.query(`
      SELECT 
        COUNT(*) AS total
      FROM 
        transactions
      JOIN 
        users ON transactions.user_id = users.id
      JOIN 
        transaction_tickets ON transactions.id = transaction_tickets.transaction_id
      JOIN 
        tickets ON transaction_tickets.ticket_id = tickets.id;
    `);

    // Menghitung total halaman
    const totalPage = Math.ceil(total / limit);

    return { rows, total, totalPage };
  } catch (error) {
    throw new Error(error.message);
  }
};

const getTransactionsTotalPriceAmountTicketsService = async (req) => {
  try {
    // Medapat data ticket dari table tickets
    let [tickets] = await pool.query(
      `SELECT id, price FROM tickets`
    );

    // Hitung total price
    let price = 0;
    for (const ticket of tickets) {
      const [rows] = await pool.query(
        `
        SELECT SUM(amount)
        FROM transaction_tickets
        WHERE ticket_id = ?;
        `,
        [ticket.id]
      );

      price += rows[0]['SUM(amount)'] * ticket.price;
    }

    return { total_price_tickets: price };
  } catch (error) {
    throw new Error(error.message);
  }
}

const getTransactionsTotalPriceAmountInventoriesService = async (req) => {
  try {
    // Medapat data inventory dari table inventories
    let [inventories] = await pool.query(
      `SELECT id, price FROM inventories`
    );

    // Hitung total price
    let price = 0;
    for (const inventory of inventories) {
      const [rows] = await pool.query(
        `
        SELECT SUM(amount)
        FROM transaction_inventories
        WHERE inventory_id = ?;
        `,
        [inventory.id]
      );

      price += rows[0]['SUM(amount)'] * inventory.price;
    }

    return { total_price_inventories: price };
  } catch (error) {
    throw new Error(error.message);
  }
}

const getSellingTicketsSummaryByDateService = async (req) => {
  try {
    const { start_date, end_date } = req.query;

    // Validasi input tanggal
    if (!start_date || !end_date) {
      throw new Error('start_date dan end_date harus diisi pada query params.');
    }

    // Validasi format tanggal (opsional, bisa tambahkan library seperti moment.js atau day.js)
    if (isNaN(Date.parse(start_date)) || isNaN(Date.parse(end_date))) {
      throw new Error('Format tanggal tidak valid. Gunakan format YYYY-MM-DD.');
    }

    // Query untuk menghitung total pendapatan dan jumlah tiket
    const [rows] = await pool.query(`
      SELECT 
        SUM(transactions.total_price) AS total_revenue, 
        SUM(transaction_tickets.amount) AS total_tickets_sold
      FROM 
        transactions
      JOIN 
        transaction_tickets ON transactions.id = transaction_tickets.transaction_id
      WHERE 
        transactions.date BETWEEN ? AND ?
    `, [start_date, end_date]);

    // Response ke client
    return {
      start_date,
      end_date,
      total_revenue: rows[0].total_revenue || 0,
      total_tickets_sold: rows[0].total_tickets_sold || 0
    };
  } catch (error) {
    throw new Error(error.message);
  }
}

const getVisitorAmountByDateService = async (req) => {
  try {
    const { start_date, end_date } = req.query;

    // Validasi input tanggal
    if (!start_date || !end_date) {
      throw new Error('start_date dan end_date harus diisi pada query params.');
    }

    // Validasi format tanggal (opsional, bisa tambahkan library seperti moment.js atau day.js)
    if (isNaN(Date.parse(start_date)) || isNaN(Date.parse(end_date))) {
      throw new Error('Format tanggal tidak valid. Gunakan format YYYY-MM-DD.');
    }

    // Query untuk menghitung jumlah pengunjung
    const [rows] = await pool.query(`
      SELECT 
        COUNT(transaction_visitors.visitor_id) AS total_visitors
      FROM 
        transactions
      JOIN 
        transaction_tickets ON transactions.id = transaction_tickets.transaction_id
      JOIN 
        transaction_visitors ON transaction_tickets.id = transaction_visitors.transaction_ticket_id
      WHERE 
        transactions.date BETWEEN ? AND ?
    `, [start_date, end_date]);

    // Response ke client
    return {
      start_date,
      end_date,
      total_visitors: rows[0].total_visitors || 0, // Default ke 0 jika hasilnya null
    };
  } catch (error) {
    throw new Error(error.message);
  }
};


export {
  getTransactionsTotalService,
  searchTransactionsService,
  getTransactionByIdService,
  getTransactionsTotalPriceAmountService,
  getTransactionsFilterService,
  getTransactionsTotalPriceAmountTicketsService,
  getTransactionsTotalPriceAmountInventoriesService,
  getSellingTicketsSummaryByDateService,
  getVisitorAmountByDateService,  
}
