import multer, { memoryStorage } from 'multer';

// Konfigurasi untuk menyimpan file di dalam memori atau RAM dalam bentuk buffer
const storage = memoryStorage();
const uploadPicture = multer({
  storage: multer.memoryStorage(),
  // Periksa apakah file adalah gambar
  fileFilter: (req, file, cb) => {
    // Jika tidak ada file yang diupload
    if (!file) {
      return cb(null, true);
    }
    if (!file.mimetype.startsWith('image/')) {
      return cb(new Error('File harus berupa gambar'), false);
    }
    cb(null, true);
  },
});

export { 
  uploadPicture
}