import { pool } from '../configs/dbConfig.js';

const addInventoryService = async (req) => {
  try {
    const { code, name, price, stock_quantity } = req.body;
    const picture = req.file;
    if (!code || !name || !price || !stock_quantity || !picture) {
      throw new Error('Semua field harus diisi');
    }

    // validasi code
    const codeRegex = /^[A-Z0-9]{5}$/;;
    if (!codeRegex.test(code)) {
      throw new Error('Code harus terdiri dari 5 huruf kapital dan angka');
    }
    // validasi price
    const priceRegex = /^\d{4,}$/;;
    if (!priceRegex.test(price)) {
      throw new Error('Price minimal 1000');
    }
    // validasi stock_quantity
    if (stock_quantity < 0) {
      throw new Error('stock_quantity minimal 1');
    }

    // Konversi gambar ke Buffer
    const pictureBuffer = picture.buffer;

    const [rows] = await pool.query(
      'INSERT INTO inventories (code, name, price, stock_quantity, picture, admin_id) VALUES (?, ?, ?, ?, ?, ?)',
      [code, name, price, stock_quantity, pictureBuffer, 1] 
    );    
    if (rows.affectedRows === 0) {
      throw new Error('Gagal menambahkan inventory');
    }

    return { code, name, price, stock_quantity, picture: 'Uploaded' };
  } catch (error) {
    throw new Error(error.message);  
  }
};

const searchInventoriesService = async (req) => {
  try {
    const { query = '', page = 1 } = req.query; // Query dan page dari query string
    const limit = 4; // 4 baris per halaman
    const offset = (page - 1) * limit; // Menghitung offset berdasarkan halaman

    // Menjalankan query pencarian
    let [rows] = await pool.query(
      `
      SELECT id, code, name, price, stock_quantity, picture
      FROM inventories 
      WHERE 
        (code LIKE ? OR name LIKE ? OR price LIKE ? OR stock_quantity LIKE ?)
      LIMIT ? OFFSET ?;
      `,
      [`%${query}%`, `%${query}%`, `%${query}%`, `%${query}%`, limit, offset]
    );

    // Menjalankan query untuk total hasil pencarian
    const [totalRows] = await pool.query(
      `
      SELECT COUNT(*) AS total 
      FROM inventories 
      WHERE 
        (code LIKE ? OR name LIKE ? OR price LIKE ? OR stock_quantity LIKE ?);
      `,
      [`%${query}%`, `%${query}%`, `%${query}%`, `%${query}%`]
    );

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
};

const getInventoryByIdService = async (req) => {
  try {
    const id = req.params.id;
    const [rows] = await pool.query('SELECT id, code, name, price, stock_quantity, picture FROM inventories WHERE id = ?', [id]);
    if (rows.length === 0) {
      throw new Error('Inventory tidak ditemukan');
    }

    // Konversi gambar dari buffer ke base64
    rows[0].picture = rows[0].picture ? `data:image/jpeg;base64,${Buffer.from(rows[0].picture).toString('base64')}` : null;
    return rows[0];
  } catch (error) {
    throw new Error(error.message);
  }
};

const updateInventoryService = async (req) => {
  try {
    const id = req.params.id;
    const { code, name, price, stock_quantity } = req.body;
    const picture = req.file ? req.file.buffer : null; 
    if (!code || !name || !price || !stock_quantity) {
      throw new Error('Semua field harus diisi');
    }

    // Validasi code
    const codeRegex = /^[A-Z0-9]{5}$/;
    if (!codeRegex.test(code)) {
      throw new Error('Code harus terdiri dari 5 huruf kapital dan angka');
    }
    // Validasi price
    const priceRegex = /^\d{4,}$/;
    if (!priceRegex.test(price)) {
      throw new Error('Price minimal 1000');
    }
    // Validasi stock_quantity
    if (stock_quantity < 0) {
      throw new Error('Stock_quantity minimal 1');
    }

    // Menyusun kueri untuk pembaruan, hanya memperbarui kolom gambar jika ada
    let query = 'UPDATE inventories SET code = ?, name = ?, price = ?, stock_quantity = ?';
    const values = [code, name, price, stock_quantity];
    // Menambahkan kolom picture pada query jika ada gambar
    if (picture) {
      query += ', picture = ?'; 
      values.push(picture);
    }
    query += ' WHERE id = ?';
    values.push(id);
    const [rows] = await pool.query(query, values);
    if (rows.affectedRows === 0) {
      throw new Error('Gagal memperbarui inventory');
    }

    if (picture) {
      return { code, name, price, stock_quantity, picture: 'Updated' };
    } else {
      return { code, name, price, stock_quantity };
    }
  } catch (error) {
    throw new Error(error.message);
  }
};

const deleteInventoryService = async (req) => {
  try {
    const id = req.params.id;
    const [rows] = await pool.query('DELETE FROM inventories WHERE id = ?', [id]);
    if (rows.affectedRows === 0) {
      throw new Error('Gagal menghapus inventory');
    }
    return { message: 'Inventory berhasil dihapus' };
  } catch (error) {
    throw new Error(error.message);
  }
};

const getInventoriesFilterService = async (req) => {
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
      SELECT id, code, name, price, stock_quantity, picture
      FROM inventories 
      ORDER BY price ${query}
      LIMIT ? OFFSET ?;
      `,
      [limit, offset]
    );

    // Menjalankan query untuk total hasil pencarian
    const [totalRows] = await pool.query(
      `
      SELECT COUNT(*) AS total 
      FROM inventories;
      `
    );

    // Menghitung total halaman
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
  addInventoryService,
  searchInventoriesService,
  getInventoryByIdService,
  updateInventoryService,
  deleteInventoryService,
  getInventoriesFilterService,
};
