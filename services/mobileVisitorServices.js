import { pool } from '../configs/dbConfig.js';

const addVisitorService = async (req) => {
  try {
    const user_id = req.user_id;
    const { name, nik } = req.body;
    if (!name || !nik) {
      throw new Error('Nama dan NIK harus diisi');
    }

    // validasi NIK
    const nikRegex = /^\d{18}$/;
    if (!nikRegex.test(nik)) {
      throw new Error('NIK harus terdiri dari 18 angka');
    }

    // cek apakah name dan NIK sudah terdaftar pada user yang sama
    const [rows] = await pool.query(
      'SELECT name, nik FROM visitors WHERE user_id = ? AND (name = ? OR nik = ?)',
      [user_id, name, nik]
    );
    if (rows.length > 0) {
      throw new Error('Name atau NIK sudah terdaftar pada user ini');
    }

    // simpan visitor ke database
    await pool.query(
      'INSERT INTO visitors (user_id, name, nik) VALUES (?, ?, ?)',
      [user_id, name, nik]
    );

    return { name, nik };
  } catch (error) {
    throw new Error(error.message);
  }
};

const getVisitorsService = async (req) => {
  try {
    const user_id = req.user_id;
    const [rows] = await pool.query('SELECT id, name, nik FROM visitors WHERE user_id = ?', [user_id]);
    return rows;
  } catch (error) {
    throw new Error(error.message);
  }
};

const updateVisitorService = async (req) => {
  try {
    const user_id = req.user_id;
    const visitor_id = req.params.id;
    const { name, nik } = req.body;
    if (!name || !nik) {
      throw new Error('Nama dan NIK harus diisi');
    }

    // validasi NIK
    const nikRegex = /^\d{18}$/;
    if (!nikRegex.test(nik)) {
      throw new Error('NIK harus terdiri dari 18 angka');
    }

    // Cek apakah visitor milik user ini
    const [visitorRows] = await pool.query(
      'SELECT * FROM visitors WHERE id = ? AND user_id = ?',
      [visitor_id, user_id]
    );
    if (visitorRows.length === 0) {
      throw new Error('Visitor tidak ditemukan');
    }

    // simpan visitor ke database
    await pool.query(
      'UPDATE visitors SET name = ?, nik = ? WHERE id = ?',
      [name, nik, visitor_id]
    );

    return { id : visitor_id, name, nik };
  } catch (error) {
    throw new Error(error.message);
  }
};

const deleteVisitorService = async (req) => {
  try {
    const user_id = req.user_id;
    const visitor_id = req.params.id;

    const [rows] = await pool.query('DELETE FROM visitors WHERE id = ? AND user_id = ?', [visitor_id, user_id]);
    if (rows.affectedRows === 0) {
      throw new Error('Gagal menghapus visitor');
    }

    return { message: 'Visitor berhasil dihapus' };
  } catch (error) {
    throw new Error(error.message);
  }
};

export { 
  addVisitorService, 
  getVisitorsService,
  updateVisitorService,
  deleteVisitorService
};
