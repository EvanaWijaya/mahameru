# Tahap 1: Build aplikasi
FROM node:18 AS build

# Set working directory
WORKDIR /app

# Copy file package.json dan package-lock.json
COPY package.json package-lock.json ./

# Install dependencies
RUN npm install

# Copy semua file aplikasi
COPY . .

# Build aplikasi
RUN npm run build

# Tahap 2: Serve file statis dengan Nginx
FROM nginx:alpine

# Copy hasil build ke Nginx
COPY --from=build /app/dist /usr/share/nginx/html

# Expose port 80 untuk menerima traffic HTTP
EXPOSE 80

# Jalankan Nginx
CMD ["nginx", "-g", "daemon off;"]
