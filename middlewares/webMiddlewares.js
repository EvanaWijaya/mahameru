import jwt from 'jsonwebtoken';

import { pool } from '../configs/dbConfig.js';

const authMiddleware = async (req, res, next) => {
  try {
    // Ambil token dari header
    const token = req.get('Authorization');
    if (!token) {
      return res.status(401).json({ error: 'Token tidak ditemukan' });
    }

    // Verifikasi token
    const decoded = jwt.verify(token, process.env.JWT_SECRET);

    // Cek email di database
    const [rows] = await pool.query('SELECT * FROM admin WHERE email = ?', [decoded.email]);
    if (rows.length === 0) {
      return res.status(401).json({ error: 'Payload tidak valid' });
    }

    req.admin_id = rows[0].id;
    next();
  } catch (error) {
    res.status(401).json({ error: 'Token tidak valid' });
  }
};

export {
  authMiddleware
};