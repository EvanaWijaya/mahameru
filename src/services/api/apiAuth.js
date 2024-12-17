import { api } from './apiConfig';
import { toast } from 'react-toastify';

export const loginAdmin = async (email, password) => {
  try {
    const response = await api.post('/login', { email, password });
    return response.data;
  } catch (error) {
    toast.error(error.response?.data.error);
    throw new Error('Login failed! ' + error.message);
  }
};

