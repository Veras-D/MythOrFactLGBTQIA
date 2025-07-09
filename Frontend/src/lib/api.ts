import axios from 'axios';

const api = axios.create({
  baseURL: process.env.REACT_APP_API_URL || 'http://localhost:8080/api',
});

export const confirmEmail = (token: string) => {
  return api.get(`/auth/confirm?token=${token}`);
};

export default api;
