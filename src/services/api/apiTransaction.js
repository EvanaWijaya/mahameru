import { api } from './apiConfig';
import { toast } from 'react-toastify';

export const getTransactions = async (page = 1, query = '') => {
  try {
    const response = await api.get(`/transaction`, {
      params: { page, query },
    });
    return response.data;
  } catch (error) {
    toast.error(error.response?.data.error);
    throw new Error('Get transactions data failed! ' + error.message);
  }
};

export const getTransactionById = async (transactionId) => {
  try {
    const response = await api.get(`/transaction/id/${transactionId}`);
    return response.data;
  } catch (error) {
    toast.error(error.response?.data.error);
    throw new Error('Get transaction detail failed! ' + error.message);
  }
};


export const getTotalTransaction = async (page = 1, query = '') => {
  try {
    const response = await api.get(`/transaction/total_price/amount`, {
      params: { page, query },
    });
    return response.data;
  } catch (error) {
    toast.error(error.response?.data.error);
    throw new Error('Get total transactions data failed! ' + error.message);
  }
};

export const getTotalTransactionTicket = async (page = 1, query = '') => {
  try {
    const response = await api.get(`/transaction/total_price/amount/ticket`, {
      params: { page, query },
    });
    return response.data;
  } catch (error) {
    toast.error(error.response?.data.error);
    throw new Error('Get total transactions ticket data failed! ' + error.message);
  }
};

export const getTotalTransactionInventory = async (page = 1, query = '') => {
  try {
    const response = await api.get(`/transaction/total_price/amount/inventory`, {
      params: { page, query },
    });
    return response.data;
  } catch (error) {
    toast.error(error.response?.data.error);
    throw new Error('Get total transactions inventory data failed! ' + error.message);
  }
};

export const getTotalTransactionSuccess = async (page = 1, query = '') => {
  try {
    const response = await api.get(`/transaction/total`, {
      params: { page, query },
    });
    return response.data;
  } catch (error) {
    toast.error(error.response?.data.error);
    throw new Error('Get total transactions success data failed! ' + error.message);
  }
};

export const filterTransactions = async (page = 1, query = '') => {
  try {
    const response = await api.get(`/transaction/filter`, {
      params: { page, query },
    });
    return response.data;
    
  } catch (error) {
    toast.error(error.response?.data.error);
    throw new Error('Filter transactions data failed! ' + error.message);
  }
}


export const getTransactionSellingSummary = async (startDate, endDate) => {
  try {
    const response = await api.get('/transaction/selling_tickets_summary', {
      params: { start_date: startDate, end_date: endDate },
    });
    return response.data;
  } catch (error) {
    toast.error(error.response?.data.error);
    throw new Error('Get transaction selling summary failed! ' + error.message);
  }
};

