import os from 'os';
import dotenv from 'dotenv';

import app from './app.js';
import {initializeDatabase } from './configs/dbConfig.js';

dotenv.config();
const getServerIpAddress = () => {
  const interfaces = os.networkInterfaces();
  for (const name of Object.keys(interfaces)) {
    for (const iface of interfaces[name]) {
      if (iface.family === 'IPv4' && !iface.internal) {
        return iface.address;
      }
    }
  }
};

const serverIp = getServerIpAddress();
const PORT = process.env.PORT || 3000;
initializeDatabase().then(() => {
  app.listen(PORT, () => {
    console.log(`Server is running on port ${PORT} with IP address ${serverIp}`);
  })
}).catch((error) => {
  console.error(error.message);
});
