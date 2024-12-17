import { toast } from 'react-toastify';
import { api } from './apiConfig';

export const getInventorys = async (page = 1, query = '') => {
  try {
    const response = await api.get(`/inventory`, {
      params: { page, query },
    });
    return response.data;
  } catch (error) {
    toast.error(error.response?.data.error);
    throw new Error('Get inventorys data failed! ' + error.message);
  }
};

export const getInventoryById = async (inventoryId) => {
  try {
    const response = await api.get(`/inventory/id/${inventoryId}`);
    return response.data;
  } catch (error) {
    toast.error(error.response?.data.error);
    throw new Error('Get inventory data failed! ' + error.message);
  }
};

export const createInventory = async (inventoryData) => {
  try {
    const response = await api.post('/inventory', inventoryData);
    return response.data;
  } catch (error) {
    toast.error(error.response?.data.error);
    throw new Error('Create inventory failed! ' + error.message);
  }
};

export const updateInventory = async (inventoryId, inventoryData) => {
  try {
    const response = await api.put(`/inventory/${inventoryId}`, inventoryData);
    return response.data;
  } catch (error) {
    toast.error(error.response?.data.error);
    throw new Error('Update inventory failed! ' + error.message);
  }
};

export const deleteInventory = async (inventoryId) => {
  try {
    const response = await api.delete(`/inventory/${inventoryId}`);
    return response.data;
  } catch (error) {
    toast.error(error.response?.data.error);
    throw new Error('Delete inventory failed! ' + error.message);
  }
};

export const filterInventorys = async (page = 1, query = '') => {
  try {
    const response = await api.get(`/inventory/filter`, {
      params: { page, query },
    });
    return response.data;
  } catch (error) {
    toast.error(error.response?.data.error);
    throw new Error('Filter inventorys data failed! ' + error.message);
  }
};