import jwt from 'jsonwebtoken';
import bcrypt from 'bcryptjs';

import { pool } from '../configs/dbConfig.js';

const loginService = async (req) => {
  try {
    // Cek request body
    const { email, password } = req.body;
    if (!email || !password) {
      return res.status(400).json({ error: 'Email dan password harus diisi' });
    }
    // Cek email valid
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(email)) {
      throw new Error('Email tidak valid');
    }
    // Cek email
    const [rows] = await pool.query('SELECT * FROM admin WHERE email = ?', [email]);
    if (rows.length === 0) {
      throw new Error('Email atau password salah');
    }
    const admin = rows[0];
    // Verifikasi password
    const isPasswordValid = await bcrypt.compare(password, admin.password);
    if (!isPasswordValid) {
      throw new Error('Email atau password salah');
    }

    // Generate JWT token
    const token = jwt.sign({ email: admin.email }, process.env.JWT_SECRET);

    return { token };
  } catch (error) {
    throw new Error(error.message);
  }
};

const getAdminService = async (req) => {
  try {
    const [rows] = await pool.query('SELECT * FROM admin WHERE id = 1');  
    if (rows.length === 0) {
      throw new Error('Admin tidak ditemukan');
    }
    rows[0].password = undefined;
    if (rows[0].picture) {
      rows[0].picture = `data:image/jpeg;base64,${rows[0].picture.toString('base64')}`;
    }
    return rows[0];
  } catch (error) {
    throw new Error(error.message);
  }
};

const updateAdminService = async (req) => {
  try {
    // Cek request body
    const { username, phone_number, email, password, address, bio } = req.body;
    if (!username || !phone_number || !email || !password || !address || !bio) {
      throw new Error('Username, email, nomor telepon, password, address, dan bio harus diisi');
    }
    // Validasi email
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(email)) {
      throw new Error('Email tidak valid');
    }
    // Validasi phone number
    const phoneRegex = /^\d{10,15}$/;
    if (!phoneRegex.test(phone_number)) {
      throw new Error('Nomor telepon harus terdiri dari 10 angka sampai 15 angka');
    }

    // Cek apakah email baru dan lama sama
    const [checkEmail] = await pool.query('SELECT email FROM admin WHERE id = ?', [1])
    let token = null;
    if (checkEmail[0].email !== email) {
      token = jwt.sign({ email: email }, process.env.JWT_SECRET);
    }

    // Hash password
    const hashedPassword = await bcrypt.hash(password, 10);
    // Update admin
    const [rows] = await pool.query('UPDATE admin SET username = ?, phone_number = ?, email = ?, password = ?, address = ?, bio = ? WHERE id = 1', 
      [username, phone_number, email, hashedPassword, address, bio]
    );
    if (rows.affectedRows === 0) {
      throw new Error('Gagal memperbarui admin');
    }

    if(token) {
      return { message: 'Admin berhasil diperbarui', token };
    }
    return { message: 'Admin berhasil diperbarui' }; 
  } catch (error) {
    throw new Error(error.message);
  }
};

const addAdminPictureService = async (req) => {
  try {
    const picture = req.file;
      if (!picture) {
        throw new Error('Gambar harus diisi');
      }

    // Konversi gambar ke Buffer
    const pictureBuffer = picture.buffer;
    const [rows] = await pool.query('UPDATE admin SET picture = ? WHERE id = 1', [pictureBuffer]);
    if (rows.affectedRows === 0) {
      throw new Error('Gagal memperbarui gambar admin');
    }
    return { message: 'Gambar admin berhasil diperbarui' };
  } catch (error) {
    throw new Error(error.message);
  }
};

export { 
  loginService,
  getAdminService,
  updateAdminService,
  addAdminPictureService,
};