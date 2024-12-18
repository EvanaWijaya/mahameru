import { pool } from "../configs/dbConfig.js";

const searchVisitorsService = async (req) => {
  try {
    const { query = '', page = 1 } = req.query;
    const limit = 4; // Jumlah data per halaman
    const offset = (page - 1) * limit; // Menghitung offset berdasarkan halaman

    // Query untuk mengambil data dengan paginasi
    const [rows] = await pool.query(
      `SELECT 
        visitors.id AS visitor_id,
        visitors.name AS visitor_name,
        visitors.nik AS visitor_nik,
        tickets.id AS ticket_id,
        tickets.name AS ticket_name
      FROM 
        visitors
      JOIN 
        transaction_visitors ON visitors.id = transaction_visitors.visitor_id
      JOIN 
        transaction_tickets ON transaction_visitors.transaction_ticket_id = transaction_tickets.id
      JOIN 
        tickets ON transaction_tickets.ticket_id = tickets.id
      WHERE 
        (visitors.name LIKE ? OR visitors.nik LIKE ? OR tickets.name LIKE ?)
      LIMIT ? OFFSET ?;`,
      [`%${query}%`, `%${query}%`, `%${query}%`, limit, offset]
    );

    // Query untuk menghitung total data
    const [[{ total }]] = await pool.query(
      `SELECT 
        COUNT(*) AS total
      FROM 
        visitors
      JOIN 
        transaction_visitors ON visitors.id = transaction_visitors.visitor_id
      JOIN 
        transaction_tickets ON transaction_visitors.transaction_ticket_id = transaction_tickets.id
      JOIN 
        tickets ON transaction_tickets.ticket_id = tickets.id
      WHERE 
        (visitors.name LIKE ? OR visitors.nik LIKE ? OR tickets.name LIKE ?);`,
      [`%${query}%`, `%${query}%`, `%${query}%`]
    );

    // Menghitung total halaman
    const totalPages = Math.ceil(total / limit);

    return { rows, total, totalPages, };
  } catch (error) {
    throw new Error(error.message); 
  }
};

const getVisitorsFilterService = async (req) => {
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
    let [rows] = await pool.query(
      `
      SELECT 
        visitors.id AS visitor_id,
        visitors.name AS visitor_name,
        visitors.nik AS visitor_nik,
        tickets.id AS ticket_id,
        tickets.name AS ticket_name
      FROM 
        visitors
      JOIN 
        transaction_visitors ON visitors.id = transaction_visitors.visitor_id
      JOIN 
        transaction_tickets ON transaction_visitors.transaction_ticket_id = transaction_tickets.id
      JOIN 
        tickets ON transaction_tickets.ticket_id = tickets.id
      ORDER BY tickets.name ${query}
      LIMIT ? OFFSET ?;
      `,
      [limit, offset]
    );

    // Query untuk menghitung total data
    const [[{ total }]] = await pool.query(
      `SELECT 
        COUNT(*) AS total
      FROM 
        visitors
      JOIN 
        transaction_visitors ON visitors.id = transaction_visitors.visitor_id
      JOIN 
        transaction_tickets ON transaction_visitors.transaction_ticket_id = transaction_tickets.id
      JOIN 
        tickets ON transaction_tickets.ticket_id = tickets.id;`
    );

    // Menghitung total halaman
    const totalPages = Math.ceil(total / limit);

    return { rows, total, totalPages };
  } catch (error) {
    throw new Error(error.message); 
  }
}

export {
  searchVisitorsService,
  getVisitorsFilterService
}
