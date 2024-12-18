# Menggunakan Node.js 18
FROM node:18

# Set working directory
WORKDIR /app

# Menyalin package.json dan package-lock.json
COPY package.json package-lock.json ./

# Install dependencies
RUN npm install

# Menyalin seluruh aplikasi ke dalam container
COPY . .

# Perintah untuk menjalankan aplikasi (menggunakan PORT dari env)
CMD ["npm", "run", "start"]
