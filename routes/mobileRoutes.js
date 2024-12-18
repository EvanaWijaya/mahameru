import express from 'express';

import mobileControllers from '../controllers/mobileControllers.js';
import {authMiddleware} from '../middlewares/mobileMiddlewares.js';
import {uploadPicture} from '../middlewares/multerMiddleware.js';

const mobileRouter = express.Router();

// Public routes
/**
 * @swagger
 * /mobile/register:
 *   post:
 *     summary: Register User
 *     requestBody:
 *       required: true
 *       content:
 *         application/json:
 *           schema:
 *             type: object
 *             properties:
 *               username:
 *                 type: string
 *               email:
 *                 type: string
 *               password:
 *                 type: string
 *     responses:
 *       200:
 *         description: Register berhasil, token diberikan
 *       400:
 *         description: Username atau password salah
 */
mobileRouter.post('/register', mobileControllers.register);
/**
 * @swagger
 * /mobile/login:
 *   post:
 *     summary: Login User 
 *     requestBody:
 *       required: true
 *       content:
 *         application/json:
 *           schema:
 *             type: object
 *             properties:
 *               username:
 *                 type: string
 *               password:
 *                 type: string
 *     responses:
 *       200:
 *         description: Login berhasil, token diberikan
 *       400:
 *         description: Email atau password salah
 */
mobileRouter.post('/login', mobileControllers.login);

// Protected routes
mobileRouter.use(authMiddleware);

/**
 * @swagger
 * /mobile/user:
 *   get:
 *     summary: Menampilkan data user
 *     security:
 *       - bearerAuth: []
 *     responses:
 *       200:
 *         description: User berhasil ditampilkan
 *       400:
 *         description: Gagal menampilkan user
 */
mobileRouter.get('/user', mobileControllers.getUser);
/**
 * @swagger
 * /mobile/user:
 *   patch:
 *     summary: Mengubah data user
 *     security:
 *       - bearerAuth: []
 *     requestBody:
 *       required: true
 *       content:
 *         application/json:
 *           schema:
 *             type: object
 *             properties:
 *               username:
 *                 type: string
 *                 description: Nama pengguna baru (opsional)
 *               email:
 *                 type: string
 *                 description: Alamat email baru (opsional)
 *               phone_number:
 *                 type: string
 *                 description: Nomor telepon baru (opsional)
 *     responses:
 *       200:
 *         description: User berhasil diubah (jika memperbari username atau email akan mendapatkan token baru)
 *       400:
 *         description: Gagal mengubah user
 */
mobileRouter.patch('/user', mobileControllers.updateUser);
/**
 * @swagger
 * /mobile/user/picture:
 *   put:
 *     summary: Mengubah gambar user
 *     security:
 *       - bearerAuth: []
 *     requestBody:
 *       required: true
 *       content:
 *         multipart/form-data:
 *           schema:
 *             type: object
 *             properties:
 *               picture:
 *                 type: file (harus berupa gambar)
 *     responses:
 *       200:
 *         description: User picture berhasil diubah
 *       400:
 *         description: Gagal mengubah user picture
 */
mobileRouter.put('/user/picture', uploadPicture.single('picture'), mobileControllers.updateUserPicture);
/**
 * @swagger
 * /mobile/problem_report:
 *   post:
 *     summary: Menambahkan problem report baru
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
 *               about:
 *                 type: string (Harus antara 'Pertanyaan dan Informasi', 'Permintaan', 'Aspirasi dan Saran', atau 'Keluhan dan Kendala')
 *               message:
 *                 type: string
 *     responses:
 *       200: 
 *         description: Problem report berhasil ditambahkan
 *       400:
 *         description: Gagal menambahkan problem report
 */
mobileRouter.post('/problem_report', mobileControllers.createProblemReport);
/**
 * @swagger
 * /mobile/new_password:
 *   post:
 *     summary: Membuat password baru
 *     security:
 *       - bearerAuth: []
 *     requestBody:
 *       required: true
 *       content:
 *         application/json:
 *           schema:
 *             type: object
 *             properties:
 *               new_password:
 *                 type: string
 *               confirm_password:
 *                 type: string
 *               old_password:
 *                 type: string
 *     responses:
 *       200:
 *         description: Password baru berhasil dibuat
 *       400:
 *         description: Gagal membuat password baru
 */
mobileRouter.post('/new_password', mobileControllers.createNewPassword);
/**
 * @swagger
 * /mobile/visitor:
 *   post:
 *     summary: Menambahkan visitor baru
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
 *     responses:
 *       200:
 *         description: Visitor berhasil ditambahkan
 *       400:
 *         description: Gagal menambahkan visitor
 */
mobileRouter.post('/visitor', mobileControllers.addVisitor);
/**
 * @swagger
 * /mobile/visitor:
 *   get:
 *     summary: Menampilkan visitor
 *     security:
 *       - bearerAuth: []
 *     responses:
 *       200:
 *         description: Visitor berhasil ditampilkan
 *       400:
 *         description: Gagal menampilkan visitor
 */
mobileRouter.get('/visitor', mobileControllers.getVisitors);
/**
 * @swagger
 * /mobile/visitor/{id}:
 *   put:
 *     summary: Memperbarui visitor
 *     security:
 *       - bearerAuth: []
 *     parameters:
 *       - name: id
 *         in: path
 *         required: true
 *         schema:
 *           type: string
 *     requestBody:
 *       required: true
 *       content:
 *         application/json:
 *           schema:
 *             type: object
 *             properties:
 *               name:
 *                 type: string
 *               nik:
 *                 type: string
 *     responses:
 *       200:
 *         description: Visitor berhasil diperbarui
 *       400:
 *         description: Gagal memperbarui visitor
 */
mobileRouter.put('/visitor/:id', mobileControllers.updateVisitor);
/**
 * @swagger
 * /mobile/visitor/{id}:
 *   delete:
 *     summary: Menghapus visitor
 *     security:
 *       - bearerAuth: []
 *     parameters:
 *       - name: id
 *         in: path
 *         required: true
 *         schema:
 *           type: string
 *     responses:
 *       200:
 *         description: Visitor berhasil dihapus
 *       400:
 *         description: Gagal menghapus visitor
 */
mobileRouter.delete('/visitor/:id', mobileControllers.deleteVisitor);
/**
 * @swagger
 * /mobile/ticket:
 *   get:
 *     summary: Menampilkan tiket
 *     security:
 *       - bearerAuth: []
 *     responses:
 *       200:
 *         description: Tiket berhasil ditampilkan
 *       400:
 *         description: Gagal menampilkan tiket
 */
mobileRouter.get('/ticket', mobileControllers.getTickets);
/**
 * @swagger
 * /mobile/ticket/{id}:
 *   get:
 *     summary: Menampilkan tiket berdasarkan ID
 *     security:
 *       - bearerAuth: []
 *     parameters:
 *       - name: id
 *         in: path
 *         required: true
 *         schema:
 *           type: string
 *     responses:
 *       200:
 *         description: Tiket berhasil ditampilkan
 *       400:
 *         description: Gagal menampilkan tiket
 */
mobileRouter.get('/ticket/:id', mobileControllers.getTicketById);
/**
 * @swagger
 * /mobile/inventory:
 *   get:
 *     summary: Menampilkan peralatan
 *     security:
 *       - bearerAuth: []
 *     responses:
 *       200:
 *         description: Peralatan berhasil ditampilkan
 *       400:
 *         description: Gagal menampilkan peralatan
 */
mobileRouter.get('/inventory', mobileControllers.getInventories);
/**
 * @swagger
 * /mobile/transaction:
 *   post:
 *     summary: Membuat transaksi baru.
 *     description: Endpoint ini digunakan untuk membuat transaksi baru, termasuk validasi tiket, inventori (jika ada), dan pengunjung.
 *     security:
 *       - bearerAuth: []
 *     requestBody:
 *       required: true
 *       content:
 *         application/json:
 *           schema:
 *             type: object
 *             properties:
 *               date:
 *                 type: string
 *                 format: date
 *                 description: Tanggal transaksi dalam format YYYY-MM-DD.
 *                 example: "2023-08-15"
 *               tickets:
 *                 type: array
 *                 items:
 *                   type: object
 *                   properties:
 *                     id:
 *                       type: integer
 *                       description: ID tiket yang akan dibeli.
 *                     amount:
 *                       type: integer
 *                       description: Jumlah tiket yang dibeli.
 *                 example:
 *                   - id: 1
 *                     amount: 2
 *                   - id: 2
 *                     amount: 3
 *               inventories:
 *                 type: array
 *                 items:
 *                   type: object
 *                   properties:
 *                     id:
 *                       type: integer
 *                       description: ID inventori yang akan disewa.
 *                     amount:
 *                       type: integer
 *                       description: Jumlah inventori yang disewa.
 *                 example:
 *                   - id: 3
 *                     amount: 2
 *                   - id: 4
 *                     amount: 1
 *               visitors_id:
 *                 type: array
 *                 items:
 *                   type: integer
 *                 description: Daftar ID pengunjung yang termasuk dalam transaksi.
 *                 example: [1, 2, 3]
 *               total_price:
 *                 type: number
 *                 format: float
 *                 description: Total harga dari transaksi.
 *                 example: 150000.00
 *     responses:
 *       201:
 *         description: Transaksi berhasil dibuat.
 *       400:
 *         description: Permintaan tidak valid (bad request).
 */
mobileRouter.post('/transaction', mobileControllers.createTransaction );
/**
 * @swagger
 * /mobile/ticket_history:
 *   get:
 *     summary: Mendapatkan riwayat tiket user
 *     security:   
 *       - bearerAuth: []
 *     responses:
 *       200:
 *         description: Riwayat tiket berhasil ditampilkan
 *       400:
 *         description: Gagal menampilkan riwayat tiket
 */
mobileRouter.get('/ticket_history', mobileControllers.getTicketsHistory);
/**
 * @swagger
 * /mobile/ticket/review_rating:
 *   put:
 *     summary: Membuat review dan rating tiket
 *     security:   
 *       - bearerAuth: []
 *     requestBody:
 *       required: true
 *       content:
 *         application/json:
 *           schema:
 *             type: object
 *             properties:
 *               transaction_ticket_id:
 *                 type: integer
 *               rating:
 *                 type: number
 *               review:
 *                 type: string
 *     responses:
 *       200:
 *         description: Review dan rating tiket berhasil disimpan
 *       400:
 *         description: Gagal menyimpan review dan rating tiket
 */
mobileRouter.put('/ticket/review_rating', mobileControllers.createReviewRatingTicket);
/**
 * @swagger
 * /mobile/ticket_review_rating:
 *   get:
 *     summary: Mendapatkan rivew dan rating tiket user
 *     security:   
 *       - bearerAuth: []
 *     responses:
 *       200:
 *         description: Riwayat tiket berhasil ditampilkan
 *       400:
 *         description: Gagal menampilkan riwayat tiket
 */
mobileRouter.get('/ticket_review_rating', mobileControllers.getReviewRatingTicket);

export default mobileRouter;