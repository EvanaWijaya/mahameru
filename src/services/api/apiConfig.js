import axios from 'axios';

const BASE_URL = 'https://back-end-production-8658.up.railway.app';
const BASE_URL_WEB = `${BASE_URL}/web`;

const api = axios.create({
  baseURL: BASE_URL_WEB,
});

api.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('authToken');
    if (token) {
      config.headers['Authorization'] = `${token}`;
    }

    if (config.data instanceof FormData) {
      config.headers['Content-Type'] = 'multipart/form-data';
    } else if (!config.headers['Content-Type']) {
      config.headers['Content-Type'] = 'application/json';
    }

    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

export { api, BASE_URL, BASE_URL_WEB };
