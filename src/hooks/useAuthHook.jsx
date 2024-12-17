import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { loginAdmin } from '../services/api/apiAuth';

const useAuthHook = () => {
  const [error, setError] = useState('');
  const navigate = useNavigate();

  const login = async (email, password) => {
    setError('');
    try {
      const data = await loginAdmin(email, password);
      localStorage.setItem('authToken', data.data.token);
      navigate('/dashboard');
    } catch (err) {
      setError('Email atau kata sandi salah!');
    }
  };

  const logout = () => {
    localStorage.removeItem('authToken');
    navigate('/');
  };

  return { login, logout, error };
};

export default useAuthHook;
