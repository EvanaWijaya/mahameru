import { api } from './apiConfig';
import { toast } from 'react-toastify';

export const getEmployees = async (page = 1, query = '') => {
    try {
      const response = await api.get(`/employee`, {
        params: { page, query },
      });
      return response.data;
    } catch (error) {
      toast.error(error.response?.data.error);
      throw new Error('Get employees data failed! ' + error.message);
    }
  };
  
export const getEmployeeById = async (employeeId) => {
  try {
    const response = await api.get(`/employee/id/${employeeId}`);
    return response.data;
  } catch (error) {
    toast.error(error.response?.data.error);
    throw new Error('Get employee data failed! ' + error.message);
  }
};

export const createEmployee = async (employeeData) => {
  try {
    const response = await api.post('/employee', employeeData);
    return response.data;
  } catch (error) {
    toast.error(error.response?.data.error);
    throw new Error('Create employee failed! ' + error.message);
  }
};

export const updateEmployee = async (employeeId, employeeData) => {
  try {
    const response = await api.put(`/employee/${employeeId}`, employeeData);
    return response.data;
  } catch (error) {
    toast.error(error.response?.data.error);
    throw new Error('Update employee failed! ' + error.message);
  }
};

export const deleteEmployee = async (employeeId) => {
  try {
    const response = await api.delete(`/employee/${employeeId}`);
    return response.data;
  } catch (error) {
    toast.error(error.response?.data.error);
    throw new Error('Delete employee failed! ' + error.message);
  }
};
export const filterEmployees = async (page = 1, query = '') => {
  try {
    const response = await api.get(`/employee/filter`, {
      params: { page, query },
    });
    return response.data;
    
  } catch (error) {
    toast.error(error.response?.data.error);
    throw new Error('Filter employees data failed! ' + error.message);
  }
}
