// Validasi data berupa array
function validateArray(items, itemName) {
  if (!Array.isArray(items) || items.length === 0) {
    throw new Error(`Data ${itemName} harus ada dan harus berupa array.`);
  }
}

// Validasi data item objek
function validateItemFields(items, fields, itemName) {
  for (const item of items) {
    for (const field of fields) {
      if (!item[field.name] || typeof item[field.name] !== field.type || (field.min !== undefined && item[field.name] <= field.min)) {
        throw new Error(`Setiap ${itemName} harus memiliki ${field.name} yang valid.`);
      }
    }
  }
}

// Validasi unik ID objek dalam array
function validateUniqueIds(items, itemName) {
  const ids = items.map(item => item.id);
  if (new Set(ids).size !== ids.length) {
    throw new Error(`Terdapat duplikat ${itemName} ID dalam permintaan.`);
  }
}

// Validasi keberadaan data di database menggunakan ID
async function validateExistenceInDatabase(tableName, items, pool) {
  const ids = items.map(item => item.id);
  const placeholders = ids.map(() => '?').join(',');
  const [rows] = await pool.query(`SELECT id FROM ${tableName} WHERE id IN (${placeholders})`, ids);
  
  const foundIds = rows.map(row => row.id);
  const missingIds = ids.filter(id => !foundIds.includes(id));

  if (missingIds.length > 0) {
    throw new Error(`${tableName} dengan ID berikut tidak ditemukan: ${missingIds.join(', ')}`);
  }
}


// Validasi request body
// Validasi date
function validateDate(date) {
  const dateRegex = /^\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12]\d|3[01])$/;
  if (!dateRegex.test(date)) {
    throw new Error('Tanggal harus dalam format YYYY-MM-DD.');
  }

  // Parsing tanggal
  const [year, month, day] = date.split('-').map(Number);
  const inputDate = new Date(year, month - 1, day);

  // Set waktu ke awal hari untuk validasi
  const today = new Date();
  today.setHours(0, 0, 0, 0);
  inputDate.setHours(0, 0, 0, 0);

  // Cek apakah tanggal input lebih kecil dari hari ini
  if (inputDate < today) {
    throw new Error('Tanggal harus minimal hari ini.');
  }
}

// Validasi tikets
async function validateTickets(tickets, pool) {
  validateArray(tickets, 'tikets');
  validateItemFields(tickets, [{ name: 'id', type: 'number' }, { name: 'amount', type: 'number', min: 0 }], 'tiket');
  validateUniqueIds(tickets, 'tiket');
  await validateExistenceInDatabase('tickets', tickets, pool);
}

// Validasi inventories
async function validateInventories(inventories, pool) {
  if (!inventories) return;
  validateArray(inventories, 'inventory');
  validateItemFields(inventories, [{ name: 'id', type: 'number' }, { name: 'amount', type: 'number', min: 0 }], 'inventory');
  validateUniqueIds(inventories, 'inventory');
  await validateExistenceInDatabase('inventories', inventories, pool);
}

// Validasi visitors_id
async function validateVisitors(visitors_id, pool) {
  validateArray(visitors_id, 'visitor');

  // Cek apakah ada ID visitor yang duplikat
  const uniqueVisitors = new Set(visitors_id);
  if (uniqueVisitors.size !== visitors_id.length) {
    throw new Error('ID visitor tidak boleh ada yang duplikat.');
  }

  // Cek apakah id visitor ada di database
  for (const visitor_id of visitors_id) {
    const [rows] = await pool.query('SELECT id FROM visitors WHERE id = ?', [visitor_id]);
    if (rows.length === 0) {
      throw new Error(`Visitor dengan ID ${visitor_id} tidak ditemukan.`);
    }
  }
}

// Validasi total_price
function validateTotalPrice(total_price) {
  if (!total_price || typeof total_price !== 'number' || total_price <= 0) {
    throw new Error('Total price harus ada dan harus berupa angka yang lebih besar dari 0.');
  }
}

// validasi request
async function validateTransactionRequest(req, pool) {
  const { date, tickets, inventories, visitors_id, total_price } = req.body;
  if (!date || !tickets || !visitors_id || !total_price) {
    throw new Error('Permintaan tidak valid (bad request). Pastikan semua data terisi, termasuk date, tickets, inventories, visitors_id, dan total_price.');
  }

  validateDate(date);
  await validateTickets(tickets, pool);
  await validateInventories(inventories, pool);
  await validateVisitors(visitors_id, pool);
  validateTotalPrice(total_price);
}

export default validateTransactionRequest;
