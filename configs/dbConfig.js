import mysql from 'mysql2/promise';
import dotenv from 'dotenv';
import bcrypt from 'bcryptjs';

dotenv.config();

const pool = mysql.createPool({
  host: process.env.DB_HOST,
  user: process.env.DB_USER,
  password: process.env.DB_PASSWORD,
  database: process.env.DB_NAME,
  waitForConnections: true,
  connectionLimit: 10,
});

// Fungsi untuk membuat database dan tabel jika belum ada
const initializeDatabase = async () => {
  try {
    // 1. Membuat koneksi sementara untuk memastikan database tersedia
    const tempConnection = await mysql.createConnection({
      host: process.env.DB_HOST,
      user: process.env.DB_USER,
      password: process.env.DB_PASSWORD,
    });

    // 2. Membuat database jika belum ada
    await tempConnection.query(`CREATE DATABASE IF NOT EXISTS \`${process.env.DB_NAME}\``);

    // 3. Tutup koneksi sementara
    await tempConnection.end();

    // 4. Membuat pool koneksi
    const pool = mysql.createPool({
      host: process.env.DB_HOST,
      user: process.env.DB_USER,
      password: process.env.DB_PASSWORD,
      database: process.env.DB_NAME,
      waitForConnections: true,
      connectionLimit: 10,
      queueLimit: 0,
    });
    const connection = await pool.getConnection();

    // 5. membuat tabel jika belum ada
    await connection.query(`
      CREATE TABLE IF NOT EXISTS admin (
        id INT PRIMARY KEY CHECK (id = 1), 
        email VARCHAR(255) NOT NULL UNIQUE, 
        password VARCHAR(255) NOT NULL,
        username VARCHAR(255),
        address VARCHAR(255),
        phone_number VARCHAR(15),
        bio TEXT,
        picture LONGBLOB,
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
      )
    `);
    const password = await bcrypt.hash('admin123', 10);
    await connection.query(`
      INSERT INTO admin (id, email, password)
      VALUES (1, 'admin@gmail.com', ?)
      ON DUPLICATE KEY UPDATE email=email
    `, [password]);

    await connection.query(`
      CREATE TABLE IF NOT EXISTS tickets (
        id INT AUTO_INCREMENT PRIMARY KEY,
        admin_id INT NOT NULL,
        name VARCHAR(255) NOT NULL UNIQUE,
        price DECIMAL(10, 2) NOT NULL,
        stock_quantity INT NOT NULL,
        visitor_quantity INT NOT NULL,
        description TEXT,
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
        FOREIGN KEY (admin_id) REFERENCES admin(id) ON DELETE CASCADE
      )
    `);

    await connection.query(`
      CREATE TABLE IF NOT EXISTS inventories (
        id INT AUTO_INCREMENT PRIMARY KEY,
        admin_id INT NOT NULL,
        code VARCHAR(5) NOT NULL UNIQUE,
        name VARCHAR(255) NOT NULL UNIQUE,
        price DECIMAL(10, 2) NOT NULL,
        stock_quantity INT NOT NULL,
        picture LONGBLOB NOT NULL,
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
        FOREIGN KEY (admin_id) REFERENCES admin(id) ON DELETE CASCADE
      )
    `);

    await connection.query(`
      CREATE TABLE IF NOT EXISTS employees (
        id INT AUTO_INCREMENT PRIMARY KEY,
        admin_id INT NOT NULL,
        name VARCHAR(255) NOT NULL UNIQUE,
        nip VARCHAR(20) NOT NULL UNIQUE,
        position VARCHAR(255) NOT NULL,
        phone_number VARCHAR(15) NOT NULL UNIQUE,
        picture LONGBLOB NOT NULL,
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
        FOREIGN KEY (admin_id) REFERENCES admin(id) ON DELETE CASCADE
      )
    `);

    await connection.query(`
      CREATE TABLE IF NOT EXISTS users (
        id INT AUTO_INCREMENT PRIMARY KEY, 
        email VARCHAR(255) NOT NULL UNIQUE,
        username VARCHAR(255) NOT NULL UNIQUE,
        password VARCHAR(255) NOT NULL,
        phone_number VARCHAR(15),
        picture LONGBLOB,
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
      )
    `);

    await connection.query(`
      CREATE TABLE IF NOT EXISTS visitors (
        id INT AUTO_INCREMENT PRIMARY KEY, 
        user_id INT NOT NULL,
        name VARCHAR(255) NOT NULL,
        nik VARCHAR(20) NOT NULL,
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
        FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
      )
    `);

    await connection.query(`
      CREATE TABLE IF NOT EXISTS transactions (
        id VARCHAR(36) PRIMARY KEY,
        user_id INT NOT NULL,
        transaction_number VARCHAR(7) NOT NULL,
        payment_method ENUM('Transfer Bank') NOT NULL,
        total_price DECIMAL(10, 2) NOT NULL,
        date DATE NOT NULL,
        transaction_code VARCHAR(8) NOT NULL,
        status ENUM('Berhasil', 'Tertunda', 'Selesai') NOT NULL,
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
        updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL,
        FOREIGN KEY (user_id) REFERENCES users(id)
      )
    `);

    await connection.query(`
      CREATE TABLE IF NOT EXISTS transaction_inventories (
        id INT AUTO_INCREMENT PRIMARY KEY,
        transaction_id VARCHAR(36) NOT NULL, 
        inventory_id INT NOT NULL,
        amount INT NOT NULL,
        FOREIGN KEY (transaction_id) REFERENCES transactions(id) ON DELETE CASCADE,
        FOREIGN KEY (inventory_id) REFERENCES inventories(id)
      )
    `);

    await connection.query(`
      CREATE TABLE IF NOT EXISTS transaction_tickets (
        id INT AUTO_INCREMENT PRIMARY KEY,
        transaction_id VARCHAR(36) NOT NULL,
        ticket_id INT NOT NULL,
        amount INT NOT NULL,
        review TEXT,
        rating VARCHAR(5),
        FOREIGN KEY (transaction_id) REFERENCES transactions(id) ON DELETE CASCADE,
        FOREIGN KEY (ticket_id) REFERENCES tickets(id)
      )
    `);

    await connection.query(`
      CREATE TABLE IF NOT EXISTS transaction_visitors (
        id INT AUTO_INCREMENT PRIMARY KEY,
        transaction_ticket_id INT NOT NULL,
        visitor_id INT NOT NULL,
        FOREIGN KEY (transaction_ticket_id) REFERENCES transaction_tickets(id) ON DELETE CASCADE,
        FOREIGN KEY (visitor_id) REFERENCES visitors(id)
      )
    `);

    await connection.query(`
      CREATE TABLE IF NOT EXISTS problem_reports (
        id INT AUTO_INCREMENT PRIMARY KEY,
        user_id INT NOT NULL,
        name VARCHAR(255) NOT NULL,
        email VARCHAR(255) NOT NULL,
        about ENUM('Pertanyaan dan Informasi', 'Permintaan', 'Aspirasi dan Saran', 'Keluhan dan Kendala') NOT NULL,
        message TEXT NOT NULL,
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
        FOREIGN KEY (user_id) REFERENCES users(id)
      )
    `);

    // 7. Tutup koneksi
    connection.release();
    console.log('Database is ready');
  } catch (error) {
    throw new Error(error);
  }
};

export { 
  pool,
  initializeDatabase
};
