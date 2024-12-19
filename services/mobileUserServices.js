import jwt from 'jsonwebtoken';
import bcrypt from 'bcryptjs';

import { pool } from '../configs/dbConfig.js';

const registerService = async (req) => {
  try {
    const { username, email, password } = req.body;
    if (!username || !email || !password) {
      throw new Error('Username, email, dan password harus diisi');
    }

    // Cek email valid
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(email)) {
      throw new Error('Email tidak valid');
    }

    // Cek apakah username atau email sudah terdaftar
    const [rows] = await pool.query('SELECT * FROM users WHERE username = ? OR email = ?', [username, email]);
    if (rows.length > 0) {
      throw new Error('Username atau email sudah terdaftar');
    }

    // Hash password
    const hashedPassword = await bcrypt.hash(password, 10);
    
    // Simpan user ke database
    await pool.query(
      'INSERT INTO users (username, email, password) VALUES (?, ?, ?)',
      [username, email, hashedPassword]
    );

    // Generate JWT token
    const token = jwt.sign({ email, username }, process.env.JWT_SECRET);

    return { token };
  } catch (error) {
    throw new Error(error.message);
  }
};

const loginService = async (req) => {
  try {
    const { username, password } = req.body;
    if (!username || !password) {
      throw new Error('Username dan password harus diisi');
    }

    const [rows] = await pool.query('SELECT * FROM users WHERE username = ?', [username]);
    if (rows.length === 0) {
      throw new Error('Username atau password salah');
    }

    const user = rows[0];
    const isPasswordValid = await bcrypt.compare(password, user.password);
    if (!isPasswordValid) {
      throw new Error('Username atau password salah');
    }

    // Generate JWT token
    const token = jwt.sign({ email: user.email, username: user.username }, process.env.JWT_SECRET);

    return { token };
  } catch (error) { 
    throw new Error(error.message);
  }
};

const getUserService = async (req) => {
  try {
    const user_id = req.user_id;
    let [rows] = await pool.query('SELECT email, username, phone_number, picture FROM users WHERE id = ?', [user_id]);
    rows[0].picture = rows[0].picture ? `data:image/jpeg;base64,${Buffer.from(rows[0].picture).toString('base64')}` : null;
    return rows[0];
  } catch (error) {
    throw new Error(error.message);
  }
};

const updateUserService = async (req) => {
  try {
    const user_id = req.user_id;
    const { email, username, phone_number } = req.body;
    if (!email && !username && !phone_number) {
      throw new Error('Setidaknya satu dari email, username, atau nomor telepon harus diisi');
    }

    // Membangun query dinamis
    const updates = [];
    const values = [];
    if (email) {
      // validasi email
      const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
      if (!emailRegex.test(email)) {
        throw new Error('Email tidak valid');
      }

      updates.push('email = ?');
      values.push(email);
    }
    if (username) {
      // cek apakah username sudah terdaftar
      const [rows] = await pool.query('SELECT * FROM users WHERE username = ?', [username]);
      if (rows.length > 0) {
        throw new Error('Username sudah terdaftar');
      }

      updates.push('username = ?');
      values.push(username);
    }
    if (phone_number) {
      // validasi nomor telepon
      const phoneRegex = /^\d{10,15}$/;
      if (!phoneRegex.test(phone_number)) {
        throw new Error('Nomor telepon harus terdiri dari 10 angka sampai 15 angka');
      }

      updates.push('phone_number = ?');
      values.push(phone_number);
    }
    values.push(user_id);
    const query = `UPDATE users SET ${updates.join(', ')} WHERE id = ?`;
    await pool.query(query, values);

    const [user] = await pool.query('SELECT email, username, phone_number FROM users WHERE id = ?', [user_id]);
    if (email || username) {
      const token = jwt.sign({ email: user[0].email, username: user[0].username }, process.env.JWT_SECRET);
      return { user: user[0], token };
    } else {
      return user[0]; 
    }
  } catch (error) {
    throw new Error(error.message);
  }
};

const updateUserPictureService = async (req) => {
  try {
    const user_id = req.user_id;
    const picture = req.file ? req.file.buffer : null;
    if (!picture) {
      throw new Error('Gambar harus diisi');
    }

    await pool.query('UPDATE users SET picture = ? WHERE id = ?', [picture, user_id]);
    return { message: 'Gambar berhasil diupload' };
  } catch (error) {
    throw new Error(error.message);
  }
};

const createProblemReportService = async (req) => {
  try {
    const { name, email, about, message } = req.body;
    if (!name || !email || !about || !message) {
      throw new Error('Semua field harus diisi');
    }
    // validasi email
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(email)) {
      throw new Error('Email tidak valid');
    }
    // validasi about
    const allowedAboutValues = ['Pertanyaan dan Informasi', 'Permintaan', 'Aspirasi dan Saran', 'Keluhan dan Kendala'];
    if (!allowedAboutValues.includes(about)) {
      throw new Error('About harus salah satu dari: Pertanyaan dan Informasi, Permintaan, Aspirasi dan Saran, Keluhan dan Kendala');
    }

    const user_id = req.user_id;
    await pool.query(
      'INSERT INTO problem_reports (name, email, about, message, user_id) VALUES (?, ?, ?, ?, ?)', 
      [name, email, about, message, user_id]
    );
    return { message: 'Laporan masalah berhasil dikirim' };
  } catch (error) {
    throw new Error(error.message);
  }
};

const createNewPasswordService = async (req) => {
  try {
    const { old_password, new_password, confirm_password } = req.body;
    if (!old_password || !new_password || !confirm_password) {
      throw new Error('Semua field harus diisi');
    }

    // Cek apakah user ada
    const user_id = req.user_id;
    const [userData] = await pool.query('SELECT * FROM users WHERE id = ?', [user_id]);
    if (userData.length === 0) {
      throw new Error('User tidak ditemukan');
    }

    // Validasi password lama
    const password = userData[0].password;
    const isPasswordValid = await bcrypt.compare(old_password, password);
    if (!isPasswordValid) {
      throw new Error('Password lama salah');
    }
    // Validasi password baru
    if (new_password !== confirm_password) {
      throw new Error('Password baru dan konfirmasi password tidak cocok');
    }

    // Menyimpan password baru
    const hashedNewPassword = await bcrypt.hash(new_password, 10);
    await pool.query('UPDATE users SET password = ? WHERE id = ?', [hashedNewPassword, user_id]);
    return { message: 'Password berhasil diperbarui' };
  } catch (error) {
    throw new Error(error.message);
  }
};

export { 
  registerService,
  loginService,
  getUserService,
  updateUserService,
  updateUserPictureService,
  createProblemReportService,
  createNewPasswordService,
};
