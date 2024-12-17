import { api } from './apiConfig';
import { toast } from 'react-toastify';

export const getVisitors = async (page = 1, query = '') => {
  try {
    const response = await api.get(`/visitor`, {
      params: { page, query },
    });
    return response.data;
  } catch (error) {
    toast.error(error.response?.data.error);
    throw new Error('Get visitors data failed! ' + error.message);
  }
};


export const filterVisitors = async (page = 1, query = '') => {
  try {
    const response = await api.get(`/visitor/filter`, {
      params: { page, query },
    });
    return response.data;
    
  } catch (error) {
    toast.error(error.response?.data.error);
    throw new Error('Filter visitors data failed! ' + error.message);
  }
}

