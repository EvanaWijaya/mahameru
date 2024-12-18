import express from 'express';

import webControllers from '../controllers/webControllers.js';
import {authMiddleware} from '../middlewares/webMiddlewares.js';
import {uploadPicture} from '../middlewares/multerMiddleware.js';

const webRouter = express.Router();

// Public routes
/**
 * @swagger
 * /web/login:
 *   post:
 *     summary: Login untuk admin
 *     requestBody:
 *       required: true
 *       content:
 *         application/json:
 *           schema:
 *             type: object
 *             properties:
 *               email:
 *                 type: string
 *               password:
 *                 type: string
 *     responses:
 *       200:
 *         description: Login berhasil, token diberikan
 *       400:
 *         description: Email atau password salah
 */
webRouter.post('/login', webControllers.login);

// Protected routes
webRouter.use(authMiddleware);

/**
 * @swagger
 * /web/admin:
 *   get:
 *     summary: Mendapatkan data admin
 *     security:
 *       - bearerAuth: []
 *     responses:
 *       200:
 *         description: Data admin ditemukan
 *       400:
 *         description: Admin tidak ditemukan
 */
webRouter.get('/admin', webControllers.getAdmin);
/**
 * @swagger
 * /web/admin:
 *   put:
 *     summary: Memperbarui admin
 *     security:
 *       - bearerAuth: []
 *     requestBody:
 *       required: true
 *       content:
 *         application/json:
 *           schema:
 *             type: object
 *             properties:
 *               name:
 *                 type: string
 *               email:
 *                 type: string
 *               password:
 *                 type: string
 *               phone_number:
 *                 type: string
 *               address:
 *                 type: string
 *               bio:
 *                 type: string
 *     responses:
 *       200:
 *         description: Admin berhasil diperbarui (jika memperbarui email, token diberikan)
 *       400:
 *         description: Gagal memperbarui admin
 */
webRouter.put('/admin', webControllers.updateAdmin);
/**
 * @swagger
 * /web/admin/picture:
 *   post:
 *     summary: Memperbarui gambar admin
 *     security:
 *       - bearerAuth: []
 *     requestBody:
 *       required: true
 *       content:
 *         application/json:
 *           schema:
 *             type: object
 *             properties:
 *               picture:
 *                 type: file (harus berupa gambar)
 *     responses:
 *       200:
 *         description: Gambar admin berhasil diperbarui
 *       400:
 *         description: Gagal memperbarui gambar admin
 */
webRouter.post('/admin/picture', uploadPicture.single('picture'), webControllers.addAdminPicture);
/**
 * @swagger
 * /web/ticket:
 *   post:
 *     summary: Menambahkan tiket baru
 *     security:
 *       - bearerAuth: []
 *     requestBody:
 *       required: true
 *       content:
 *         application/json:
 *           schema:
 *             type: object
 *             properties:
 *               name:
 *                 type: string
 *               price:
 *                 type: number
 *               amount:
 *                 type: number
 *               description:
 *                 type: string
 *     responses:
 *       200:
 *         description: Tiket berhasil ditambahkan
 *       400:
 *         description: Gagal menambahkan tiket
 */
webRouter.post('/ticket', webControllers.addTicket);
/**
 * @swagger
 * /web/ticket:
 *   get:
 *     summary: Mencari tiket dengan pagination
 *     security:
 *       - bearerAuth: []
 *     parameters:
 *       - in: query
 *         name: query
 *         schema:
 *           type: string
 *         required: false
 *         description: Kata kunci pencarian (sebagai contoh, name, price, stock quantity, visitor quantity, atau description).
 *       - in: query
 *         name: page
 *         schema:
 *           type: integer
 *           default: 1
 *         required: false
 *         description: Nomor halaman (default 1).
 *     responses:
 *       200:
 *         description: list tiket dengan pagination
 *       500:
 *         description: Internal server error
 */
webRouter.get('/ticket', webControllers.searchTickets);
/**
 * @swagger
 * /web/ticket/id/{id}:
 *   get:
 *     summary: Mendapatkan tiket berdasarkan ID
 *     security:
 *       - bearerAuth: []
 *     parameters:
 *       - in: path
 *         name: id
 *         schema:
 *           type: integer
 *         required: true
 *     responses:
 *       200:
 *         description: Tiket ditemukan
 *       400:
 *         description: Tiket tidak ditemukan
 */
webRouter.get('/ticket/id/:id', webControllers.getTicketById);
/**
 * @swagger
 * /web/ticket/{id}:
 *   put:
 *     summary: Memperbarui tiket
 *     security:
 *       - bearerAuth: []
 *     parameters:
 *       - in: path
 *         name: id
 *         schema:
 *           type: integer
 *         required: true
 *     requestBody:
 *       required: true
 *       content:
 *         application/json:
 *           schema:
 *             type: object
 *             properties:
 *               name:
 *                 type: string
 *               price:
 *                 type: number
 *               stock_quantity:
 *                 type: number
 *               visitor_quantity:
 *                 type: number
 *               description:
 *                 type: string
 *     responses:
 *       200:
 *         description: Tiket berhasil diperbarui
 *       400:
 *         description: Gagal memperbarui tiket
 */
webRouter.put('/ticket/:id', webControllers.updateTicket);
/**
 * @swagger
 * /web/ticket/{id}:
 *   delete:
 *     summary: Menghapus tiket
 *     security:
 *       - bearerAuth: []
 *     parameters:
 *       - in: path
 *         name: id
 *         schema:
 *           type: integer
 *         required: true
 *     responses:
 *       200:
 *         description: Tiket berhasil dihapus
 *       400:
 *         description: Gagal menghapus tiket
 */
webRouter.delete('/ticket/:id', webControllers.deleteTicket);
/**
 * @swagger
 * /web/ticket/filter:
 *   get:
 *     summary: Mendapatkan tiket berdasarkan filter harga dan pagination 
 *     security:
 *       - bearerAuth: []
 *     parameters:
 *       - in: query
 *         name: query
 *         schema:
 *           type: string
 *         required: false
 *         description: Kata kunci filter (hanya dapat menggunakan ASC dan DESC).
 *       - in: query
 *         name: page
 *         schema:
 *           type: integer
 *           default: 1
 *         required: false
 *         description: Nomor halaman (default 1).
 *     responses:
 *       200:
 *         description: List tiket berdasarkan filter
 *       500:
 *         description: Internal server error
 */
webRouter.get('/ticket/filter', webControllers.getTicketsFilter);
/**
 * @swagger
 * /web/inventory:
 *   post:
 *     summary: Menambahkan inventaris baru
 *     security:
 *       - bearerAuth: []
 *     requestBody:
 *       required: true
 *       content:
 *         application/json:
 *           schema:
 *             type: object
 *             properties:
 *               code:
 *                 type: string
 *               name:
 *                 type: string
 *               price:
 *                 type: number
 *               stock_quantity:
 *                 type: number
 *               picture:
 *                 type: file (harus berupa gambar)
 *     responses:
 *       200:
 *         description: Inventaris berhasil ditambahkan
 *       400:
 *         description: Gagal menambahkan inventaris
 */
webRouter.post('/inventory', uploadPicture.single('picture'), webControllers.addInventory);
/**
 * @swagger
 * /web/inventory:
 *   get:
 *     summary: Mencari inventaris dengan pagination
 *     security:
 *       - bearerAuth: []
 *     parameters:
 *       - in: query
 *         name: query
 *         schema:
 *           type: string
 *         required: false
 *         description: Kata kunci pencarian (sebagai contoh, code, name, price, atau stock quantity).
 *       - in: query
 *         name: page
 *         schema:
 *           type: integer
 *           default: 1
 *         required: false
 *         description: Nomor halaman (default 1).
 *     responses:
 *       200:
 *         description: list inventaris dengan pagination
 *       500:
 *         description: Internal server error
 */
webRouter.get('/inventory', webControllers.searchInventories);
/**
 * @swagger
 * /web/inventory/id/{id}:
 *   get:
 *     summary: Mendapatkan inventaris berdasarkan ID
 *     security:
 *       - bearerAuth: []
 *     parameters:
 *       - in: path
 *         name: id
 *         schema:
 *           type: integer
 *         required: true
 *     responses:
 *       200:
 *         description: Inventaris ditemukan
 *       400:
 *         description: Inventaris tidak ditemukan
 * */
webRouter.get('/inventory/id/:id', webControllers.getInventorybyId);
/**
 * @swagger
 * /web/inventory/{id}:
 *   put:
 *     summary: Memperbarui inventaris
 *     security:
 *       - bearerAuth: []
 *     parameters:
 *       - in: path
 *         name: id
 *         schema:
 *           type: integer
 *         required: true
 *     requestBody:
 *       required: true
 *       content:
 *         application/json:
 *           schema:
 *             type: object
 *             properties:
 *               code:
 *                 type: string
 *               name:
 *                 type: string
 *               price:
 *                 type: number
 *               stock_quantity:
 *                 type: number
 *               picture:
 *                 type: file (harus berupa gambar) (optional)
 *     responses:
 *       200:
 *         description: Inventaris berhasil diperbarui
 *       400:
 *         description: Gagal memperbarui inventaris
 */
webRouter.put('/inventory/:id', uploadPicture.single('picture'), webControllers.updateInventory);
/**
 * @swagger
 * /web/inventory/{id}:
 *   delete:
 *     summary: Menghapus inventaris
 *     security:
 *       - bearerAuth: []
 *     parameters:
 *       - in: path
 *         name: id
 *         schema:
 *           type: integer
 *         required: true
 *     responses:
 *       200:
 *         description: Inventaris berhasil dihapus
 *       400:
 *         description: Gagal menghapus inventaris
 */
webRouter.delete('/inventory/:id', webControllers.deleteInventory);
/**
 * @swagger
 * /web/inventory/filter:
 *   get:
 *     summary: Mencari inventaris dengan filter
 *     security:
 *       - bearerAuth: []
 *     parameters:
 *       - in: query
 *         name: query
 *         schema:
 *           type: string
 *         required: false
 *         description: Kata kunci pencarian (hanya dapat menggunakan ASC dan DESC).
 *       - in: query
 *         name: page
 *         schema:
 *           type: integer
 *           default: 1
 *         required: false
 *         description: Nomor halaman (default 1).
 *     responses:
 *       200:
 *         description: list inventaris dengan filter
 *       500:
 *         description: Internal server error
 */
webRouter.get('/inventory/filter', webControllers.getInventoriesFilter);
/**
 * @swagger
 * /web/employee:
 *   post:
 *     summary: Menambahkan pegawai baru
 *     security:
 *       - bearerAuth: []
 *     requestBody:
 *       required: true
 *       content:
 *         application/json:
 *           schema:
 *             type: object
 *             properties:
 *               name:
 *                 type: string
 *               nip:
 *                 type: string
 *               position:
 *                 type: string
 *               phone_number:
 *                 type: string
 *               picture:
 *                 type: file (harus berupa gambar)
 *     responses:
 *       200:
 *         description: Pegawai berhasil ditambahkan
 *       400:
 *         description: Gagal menambahkan pegawai
 */
webRouter.post('/employee', uploadPicture.single('picture'), webControllers.addEmployee);
/**
 * @swagger
 * /web/employee:
 *   get:
 *     summary: Mencari pegawai dengan pagination
 *     security:
 *       - bearerAuth: []
 *     parameters:
 *       - in: query
 *         name: query
 *         schema:
 *           type: string
 *         required: false
 *         description: Kata kunci pencarian (sebagai contoh, NIP, name, position, atau phone number).
 *       - in: query
 *         name: page
 *         schema:
 *           type: integer
 *           default: 1
 *         required: false
 *         description: Nomor halaman (default 1).
 *     responses:
 *       200:
 *         description: List pegawai dengan pagination
 *       500:
 *         description: Internal server error
 */
webRouter.get('/employee', webControllers.searchEmployees);
/**
 * @swagger
 * /web/employee/id/{id}:
 *   get:
 *     summary: Mendapatkan pegawai berdasarkan ID
 *     security:
 *       - bearerAuth: []
 *     parameters:
 *       - in: path
 *         name: id
 *         schema:
 *           type: integer
 *         required: true
 *     responses: 
 *       200:
 *         description: Pegawai ditemukan
 *       400:
 *         description: Pegawai tidak ditemukan
 */
webRouter.get('/employee/id/:id', webControllers.getEmployeeById);
/**
 * @swagger
 * /web/employee/{id}:
 *   put:
 *     summary: Memperbarui pegawai
 *     security:
 *       - bearerAuth: []
 *     parameters:
 *       - in: path
 *         name: id
 *         schema:
 *           type: integer
 *         required: true
 *     requestBody:
 *       required: true
 *       content:
 *         application/json:
 *           schema:
 *             type: object
 *             properties:
 *               name:
 *                 type: string
 *               nip:
 *                 type: string
 *               position:
 *                 type: string
 *               phone_number:
 *                 type: string
 *               picture:
 *                 type: file (harus berupa gambar) (optional)
 *     responses:
 *       200:
 *         description: Pegawai berhasil diperbarui
 *       400:
 *         description: Gagal memperbarui pegawai
 */
webRouter.put('/employee/:id', uploadPicture.single('picture'), webControllers.updateEmployee);
/**
 * @swagger
 * /web/employee/{id}:
 *   delete:
 *     summary: Menghapus pegawai
 *     security:
 *       - bearerAuth: []
 *     parameters:
 *       - in: path
 *         name: id
 *         schema:
 *           type: integer
 *         required: true
 *     responses:
 *       200:
 *         description: Pegawai berhasil dihapus
 *       400:
 *         description: Gagal menghapus pegawai
 */
webRouter.delete('/employee/:id', webControllers.deleteEmployee);
/**
 * @swagger
 * /web/employee/filter:
 *   get:
 *     summary: Mencari pegawai dengan filter dan pagination
 *     security:
 *       - bearerAuth: []
 *     parameters:
 *       - in: query
 *         name: query
 *         schema:
 *           type: string
 *         required: false
 *         description: Kata kunci pencarian (hanya dapat menggunakan ASC dan DESC).
 *       - in: query
 *         name: page
 *         schema:
 *           type: integer
 *           default: 1
 *         required: false
 *         description: Nomor halaman (default 1).
 *     responses:
 *       200:
 *         description: list pegawai dengan filter
 *       500:
 *         description: Internal server error
 */
webRouter.get('/employee/filter', webControllers.getEmployeesFilter);
/**
 * @swagger
 * /web/visitor:
 *   get:
 *     summary: Mencari pengunjung dengan pagination
 *     security:
 *       - bearerAuth: []
 *     parameters:
 *       - in: query
 *         name: query
 *         schema:
 *           type: string
 *         required: false
 *         description: Kata kunci pencarian (sebagai contoh, name, NIK, atau Tipe tiket).
 *       - in: query
 *         name: page
 *         schema:
 *           type: integer
 *           default: 1
 *         required: false
 *         description: Nomor halaman (default 1).
 *     responses:
 *       200:
 *         description: List pengunjung dengan pagination
 *       500:
 *         description: Internal server error
 */
webRouter.get('/visitor', webControllers.searchVisitors);
/**
 * @swagger
 * /web/visitor/filter:
 *   get:
 *     summary: Mencari pengunjung dengan filter dan pagination
 *     security:
 *       - bearerAuth: []
 *     parameters:
 *       - in: query
 *         name: query
 *         schema:
 *           type: string
 *         required: false
 *         description: Kata kunci pencarian (hanya dapat menggunakan ASC dan DESC).
 *       - in: query
 *         name: page
 *         schema:
 *           type: integer
 *           default: 1
 *         required: false
 *         description: Nomor halaman (default 1).
 *     responses:
 *       200:
 *         description: List pengunjung dengan filter
 *       500:
 *         description: Internal server error
 */
webRouter.get('/visitor/filter', webControllers.getVisitorsFilter);
/**
 * @swagger
 * /web/transaction:
 *   get:
 *     summary: Mencari transaksi dengan pagination
 *     security:
 *       - bearerAuth: []
 *     parameters:
 *       - in: query
 *         name: query
 *         schema:
 *           type: string
 *         required: false
 *         description: Kata kunci pencarian (sebagai contoh, tanggal, username, Jenis tiket, nominal, atau status).
 *       - in: query
 *         name: page
 *         schema:
 *           type: integer
 *           default: 1
 *         required: false
 *         description: Nomor halaman (default 1).
 *     responses:
 *       200:
 *         description: List transaksi dengan pagination
 *       500:
 *         description: Internal server error
 */
webRouter.get('/transaction', webControllers.searchTransactions);
/**
 * @swagger
 * /web/transaction/total:
 *   get:
 *     summary: Mendapatkan jumlah total transaksi yang ada
 *     security:
 *       - bearerAuth: []
 *     responses:
 *       200:
 *         description: Total transaksi
 *       400:
 *         description: Gagal mendapatkan total transaksi
 */
webRouter.get('/transaction/total', webControllers.getTransactionsTotal);
/**
 * @swagger
 * /web/transaction/id/{id}:
 *   get:
 *     summary: Mendapatkan transaksi berdasarkan ID
 *     security:
 *       - bearerAuth: []
 *     responses:
 *       200:
 *         description: Transaksi
 *       400:
 *         description: Gagal mendapatkan transaksi
 */
webRouter.get('/transaction/id/:id', webControllers.getTransactionById);
/**
 * @swagger
 * /web/transaction/total_price/amount:
 *   get:
 *     summary: Mendapatkan total harga seluruh transaksi
 *     security:
 *       - bearerAuth: []
 *     responses:
 *       200:
 *         description: Total harga seluruh transaksi
 *       400:
 *         description: Gagal mendapatkan total harga seluruh transaksi
 */
webRouter.get('/transaction/total_price/amount', webControllers.getTransactionsTotalPriceAmount);
/**
 * @swagger
 * /web/transaction/filter:
 *   get:
 *     summary: Mendapatkan transaksi berdasarkan filter dan pagination
 *     security:
 *       - bearerAuth: []
 *     parameters:
 *       - in: query
 *         name: query
 *         schema:
 *           type: string
 *         required: false
 *         description: Kata kunci filter (hanya dapat menggunakan kata kunci 'start_date', 'end_date', 'type', dan 'status').
 *       - in: query
 *         name: page
 *         schema:
 *           type: integer
 *           default: 1
 *         required: false
 *         description: Nomor halaman (default 1).
 *     responses:
 *       200:
 *         description: Transaksi
 *       400:
 *         description: Gagal mendapatkan transaksi
 */
webRouter.get('/transaction/filter', webControllers.getTransactionsFilter);
/**
 * @swagger
 * /web/transaction/total_price/amount/ticket:
 *   get:
 *     summary: Mendapatkan total harga tiket seluruh transaksi
 *     security:
 *       - bearerAuth: []
 *     responses:
 *       200:
 *         description: Total harga tiket seluruh transaksi
 *       400:
 *         description: Gagal mendapatkan total harga tiket seluruh transaksi
 */
webRouter.get('/transaction/total_price/amount/ticket', webControllers.getTransactionsTotalPriceAmountTickets);
/**
 * @swagger
 * /web/transaction/total_price/amount/inventory:
 *   get:
 *     summary: Mendapatkan total harga inventori seluruh transaksi
 *     security:
 *       - bearerAuth: []
 *     responses:
 *       200:
 *         description: Total harga inventori seluruh transaksi
 *       400:
 *         description: Gagal mendapatkan total harga inventori seluruh transaksi
 */
webRouter.get('/transaction/total_price/amount/inventory', webControllers.getTransactionsTotalPriceAmountInventories);
/**
 * @swagger
 * /web/transaction/selling_tickets_summary:
 *   get:
 *     summary: Mendapatkan total tiket yang terjual per tanggal
 *     security:
 *       - bearerAuth: []
 *     parameters:
 *       - in: query
 *         name: start_date
 *         schema:
 *           type: string
 *         required: true
 *         description: Tanggal mulai pencarian, format YYYY-MM-DD
 *       - in: query
 *         name: end_date
 *         schema:
 *           type: string
 *         required: true
 *         description: Tanggal akhir pencarian, format YYYY-MM-DD
 *     responses:
 *       200:
 *         description: Total tiket yang terjual per tanggal
 *       400:
 *         description: Gagal mendapatkan total tiket yang terjual per tanggal
 */
webRouter.get('/transaction/selling_tickets_summary', webControllers.getSellingTicketsSummaryByDate);
/**
 * @swagger
 * /web/transaction/visitor_amount:
 *   get:
 *     summary: Mendapatkan total visitor yang berkunjung per tanggal
 *     security:
 *       - bearerAuth: []
 *     parameters:
 *       - in: query
 *         name: start_date
 *         schema:
 *           type: string
 *         required: true
 *         description: Tanggal mulai pencarian, format YYYY-MM-DD
 *       - in: query
 *         name: end_date
 *         schema:
 *           type: string
 *         required: true
 *         description: Tanggal akhir pencarian, format YYYY-MM-DD
 *     responses:
 *       200:
 *         description: Total visitor yang berkunjung per tanggal
 *       400:
 *         description: Gagal mendapatkan total visitor yang berkunjung per tanggal
 */
webRouter.get('/transaction/visitor_amount', webControllers.getVisitorAmountByDate);

export default webRouter;
