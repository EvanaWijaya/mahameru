import { api } from './apiConfig';
import { toast } from 'react-toastify';

export const getProfile = async () => {
  try {
    const response = await api.get('/admin');
    return response.data;
  } catch (error) {
    toast.error(error.response?.data.error);
    throw new Error('Get profile data failed! ' + error.message);
  }
};

export const updateProfile = async (profileData) => {
  try {
    const response = await api.put('/admin', profileData);
    return response.data;
  } catch (error) {
    toast.error(error.response?.data.error);
    throw new Error('Update profile data failed! ' + error.message);
  }
};

export const addProfilePicture = async (profilePicture) => {
  try {
    const response = await api.post('/admin/picture', profilePicture);
    return response.data;
  } catch (error) {
    toast.error(error.response?.data.error);
    throw new Error('Add profile picture failed! ' + error.message);
  }
};