import { pool } from '../configs/dbConfig.js';

const addTicketService = async (req) => {
  try {
    const { name, price, stock_quantity, visitor_quantity, description } = req.body;
    if (!name || !price || !stock_quantity || !visitor_quantity || !description) {
      throw new Error('Semua field harus diisi');
    }

    // validasi price
    const priceRegex = /^\d{4,}$/;;
    if (!priceRegex.test(price)) {
      throw new Error('Price minimal 1000');
    }
    // validasi stock_quantity
    const visitor_quantityRegex = /^[1-5]$/;;
    if (!visitor_quantityRegex.test(visitor_quantity)) {
      throw new Error('visitor_quantity harus berjumlah antara 1 sampai 5');
    }
    // validasi stock_quantity dan visitor_quantity
    if (stock_quantity < 0) {
      throw new Error('stock_quantity atau visitor_quantity minimal 1');
    }

    const [rows] = await pool.query(
      'INSERT INTO tickets (name, price, stock_quantity, visitor_quantity, description, admin_id) VALUES (?, ?, ?, ?, ?, ?)',
      [name, price, stock_quantity, visitor_quantity, description, 1] 
    );    
    if (rows.affectedRows === 0) {
      throw new Error('Gagal menambahkan tiket');
    }

    return { name, price, stock_quantity, visitor_quantity, description };
  } catch (error) {
    throw new Error(error.message);
  }
};

const searchTicketsService = async (req) => {
  try {
    const { query = '', page = 1 } = req.query; // Query dan page dari query string
    const limit = 4; // 4 baris per halaman
    const offset = (page - 1) * limit; // Menghitung offset berdasarkan halaman

    // Menjalankan query pencarian
    const [rows] = await pool.query(
      `
      SELECT id, name, price, stock_quantity, visitor_quantity, description
      FROM tickets 
      WHERE 
        (name LIKE ? OR price LIKE ? OR stock_quantity LIKE ? OR visitor_quantity LIKE ? OR description LIKE ?)
      LIMIT ? OFFSET ?;
      `,
      [`%${query}%`, `%${query}%`, `%${query}%`, `%${query}%`, `%${query}%`, limit, offset]
    );

    // Menjalankan query untuk total hasil pencarian
    const [totalRows] = await pool.query(
      `
      SELECT COUNT(*) AS total 
      FROM tickets 
      WHERE 
        (name LIKE ? OR price LIKE ? OR stock_quantity LIKE ? OR visitor_quantity LIKE ? OR description LIKE ?);
      `,
      [`%${query}%`, `%${query}%`, `%${query}%`, `%${query}%`, `%${query}%`]
    );

    const total = totalRows[0].total; // Total hasil pencarian
    const totalPages = Math.ceil(total / limit); // Total halaman

    return { rows, total, totalPages };
  } catch (error) {
    throw new Error(error.message);
  }
}

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

const updateTicketService = async (req) => {
  try {
    const id = req.params.id;
    const { name, price, stock_quantity, visitor_quantity, description } = req.body;
    if (!name || !price || !stock_quantity || !visitor_quantity || !description) {
      throw new Error('Semua field harus diisi');
    }

    // validasi price
    const priceRegex = /^\d{4,}$/;;
    if (!priceRegex.test(price)) {
      throw new Error('Price minimal 1000');
    }
    // validasi stock_quantity
    const visitor_quantityRegex = /^[1-5]$/;;
    if (!visitor_quantityRegex.test(visitor_quantity)) {
      throw new Error('visitor_quantity harus berjumlah antara 1 sampai 5');
    }
    // validasi stock_quantity dan visitor_quantity
    if (stock_quantity < 0) {
      throw new Error('stock_quantity atau visitor_quantity minimal 1');
    }

    const [rows] = await pool.query(
      'UPDATE tickets SET name = ?, price = ?, stock_quantity = ?, visitor_quantity = ?, description = ? WHERE id = ?',
      [name, price, stock_quantity, visitor_quantity, description, id]
    );
    if (rows.affectedRows === 0) {
      throw new Error('Gagal memperbarui ticket');
    }
    return { name, price, stock_quantity, visitor_quantity, description };
  } catch (error) {
    throw new Error(error.message);
  }
};

const deleteTicketService = async (req) => {
  try {
    const id = req.params.id;
    const [rows] = await pool.query('DELETE FROM tickets WHERE id = ?', [id]);
    if (rows.affectedRows === 0) {
      throw new Error('Gagal menghapus ticket');
    }
    return { message: 'Ticket berhasil dihapus' };
  } catch (error) {
    throw new Error(error.message);
  }
};

const getTicketsFilterService = async (req) => {
  try {
    const { query = '', page = 1 } = req.query; 
    // Validasi query
    const validQueries = ['ASC', 'DESC'];
    if (query && !validQueries.includes(query)) {
      throw new Error('Query tidak valid. Silakan gunakan ASC atau DESC.');
    }

    // Menentukan kolom dan arah pengurutan untuk ORDER BY
    const limit = 4; // Jumlah data per halaman
    const offset = (page - 1) * limit; // Menghitung offset berdasarkan halaman
    const [rows] = await pool.query(
      `
      SELECT id, name, price, stock_quantity, visitor_quantity, description
      FROM tickets 
      ORDER BY price ${query}
      LIMIT ? OFFSET ?;
      `,
      [limit, offset]
    );

    // Menjalankan query untuk total hasil pencarian
    const [totalRows] = await pool.query(
      `
      SELECT COUNT(*) AS total 
      FROM tickets;
      `
    );

    const total = totalRows[0].total; // Total hasil pencarian
    const totalPages = Math.ceil(total / limit); // Total halaman  

    return { rows, total, totalPages };
  } catch (error) {
    throw new Error(error.message);
  }
}

export {
  addTicketService,
  searchTicketsService,
  getTicketByIdService,
  updateTicketService,
  deleteTicketService,
  getTicketsFilterService,
}