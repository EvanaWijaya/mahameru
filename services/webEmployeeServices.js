import { pool } from '../configs/dbConfig.js';

const addEmployeeService = async (req) => {
  try {
    const { name, nip, position, phone_number } = req.body;
    const picture = req.file;
    if (!name || !nip || !position || !phone_number || !picture) {
      throw new Error('Semua field harus diisi');
    }

    // validasi nip
    const nipRegex = /^\d{18}$/;
    if (!nipRegex.test(nip)) {
      throw new Error('NIP harus terdiri dari 18 angka');
    }
    // validasi nomor telepon
    const phoneRegex = /^\d{10,15}$/;
    if (!phoneRegex.test(phone_number)) {
      throw new Error('Nomor telepon harus terdiri dari 10 angka sampai 15 angka');
    }

    // Konversi gambar ke Buffer
    const pictureBuffer = picture.buffer;

    const [rows] = await pool.query(
      'INSERT INTO employees (name, nip, position, phone_number, picture, admin_id) VALUES (?, ?, ?, ?, ?, ?)',
      [name, nip, position, phone_number, pictureBuffer, 1]
    );

    if (rows.affectedRows === 0) {  
      throw new Error('Gagal menambahkan pegawai');
    }

    return { name, nip, position, phone_number, picture: 'Uploaded' };
  } catch (error) {
    throw new Error(error.message);
  }
};

const searchEmployeesService = async (req) => {
  try {
    const { query = '', page = 1 } = req.query; // Query dan page dari query string
    const limit = 4; // 4 baris per halaman
    const offset = (page - 1) * limit; // Menghitung offset berdasarkan halaman

    // Menjalankan query pencarian
    const [rows] = await pool.query(
      `
      SELECT id, nip, name, position, phone_number, picture
      FROM employees 
      WHERE 
        (nip LIKE ? OR name LIKE ? OR position LIKE ? OR phone_number LIKE ?)
      LIMIT ? OFFSET ?;
      `,
      [`%${query}%`, `%${query}%`, `%${query}%`, `%${query}%`, limit, offset]
    );

    // Menjalankan query untuk total hasil pencarian
    const [totalRows] = await pool.query(
      `
      SELECT COUNT(*) AS total 
      FROM employees 
      WHERE 
        (nip LIKE ? OR name LIKE ? OR position LIKE ? OR phone_number LIKE ?);
      `,
      [`%${query}%`, `%${query}%`, `%${query}%`, `%${query}%`]
    );

    const total = totalRows[0].total; // Total hasil pencarian
    const totalPages = Math.ceil(total / limit); // Total halaman

    // Konversi gambar dari buffer ke base64
    rows.forEach((employee) => {
      employee.picture = employee.picture ? `data:image/jpeg;base64,${Buffer.from(employee.picture).toString('base64')}` : null;
    });

    return { rows, total, totalPages };
  } catch (error) {
    throw new Error(error.message);
  }
};

const getEmployeeByIdService = async (req) => {
  try {
    const id = req.params.id;
    const [rows] = await pool.query('SELECT id, name, nip, position, phone_number, picture FROM employees WHERE id = ?', [id]);
    if (rows.length === 0) {
      throw new Error('Pegawai tidak ditemukan');
    }

    // Konversi gambar dari buffer ke base64
    rows[0].picture = rows[0].picture ? `data:image/jpeg;base64,${Buffer.from(rows[0].picture).toString('base64')}` : null;
    return rows[0];
  } catch (error) {
    throw new Error(error.message);
  }
}

const updateEmployeeService = async (req) => {
  try {
    const id = req.params.id;
    const { name, nip, position, phone_number } = req.body;
    const picture = req.file ? req.file.buffer : null;
    if (!name || !nip || !position || !phone_number) {
      throw new Error('Semua field harus diisi');
    }

    // validasi id
    if (id <= 0) {
      throw new Error('ID pegawai tidak valid');
    }
    // validasi nip
    const nipRegex = /^\d{18}$/;
    if (!nipRegex.test(nip)) {
      throw new Error('NIP harus terdiri dari 18 angka');
    }
    // validasi nomor telepon
    const phoneRegex = /^\d{10,15}$/;
    if (!phoneRegex.test(phone_number)) {
      throw new Error('Nomor telepon harus terdiri dari 10 angka sampai 15 angka');
    }

    // Menyusun kueri untuk pembaruan, hanya memperbarui kolom gambar jika ada
    let query = 'UPDATE employees SET name = ?, nip = ?, position = ?, phone_number = ?';
    const values = [name, nip, position, phone_number];
    // Menambahkan kolom picture pada query jika ada gambar
    if (picture) {
      query += ', picture = ?'; 
      values.push(picture);
    }
    query += ' WHERE id = ?';
    values.push(id);
    const [rows] = await pool.query(query, values);
    if (rows.affectedRows === 0) {
      throw new Error('Gagal memperbarui pegawai');
    }

    if (picture) {
      return { name, nip, position, phone_number, picture: 'Updated' };
    } else {
      return { name, nip, position, phone_number };      
    }
  } catch (error) {
    throw new Error(error.message);
  }
};

const deleteEmployeeService = async (req) => {
  try {
    const id = req.params.id;
    const [rows] = await pool.query('DELETE FROM employees WHERE id = ?', [id]);
    if (rows.affectedRows === 0) {
      throw new Error('Gagal menghapus pegawai');
    }
    return { message: 'Pegawai berhasil dihapus' };
  } catch (error) {
    throw new Error(error.message);
  }
};

const getEmployeesFilterService = async (req) => {
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
      SELECT id, nip, name, position, phone_number, picture
      FROM employees
      ORDER BY name ${query}
      LIMIT ? OFFSET ?
      `,
      [limit, offset]
    )

    // Menjalankan query untuk total hasil pencarian
    const [totalRows] = await pool.query(
      `
      SELECT COUNT(*) as total
      FROM employees
      `
    )

    const total = totalRows[0].total; // Total hasil pencarian
    const totalPages = Math.ceil(total / limit); // Total halaman

    // Konversi gambar dari buffer ke base64
    rows = rows.map((row) => ({
      ...row,
      picture: row.picture ? `data:image/jpeg;base64,${Buffer.from(row.picture).toString('base64')}` : null,
    }));

    return { rows, total, totalPages };
  } catch (error) {
    throw new Error(error.message);
  }
}

export {
  addEmployeeService,
  searchEmployeesService,
  getEmployeeByIdService,
  updateEmployeeService,
  deleteEmployeeService,
  getEmployeesFilterService,
}