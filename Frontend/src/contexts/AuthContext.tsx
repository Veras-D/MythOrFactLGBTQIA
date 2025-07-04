import React, { createContext, useContext, useState, useEffect } from 'react';
import api from '../lib/api';
import { jwtDecode } from 'jwt-decode';

export interface User {
  id: number;
  username: string;
  email: string;
  highestScore: number;
  createdAt: string;
}

export interface GameHistory {
  id: number;
  user_id: number;
  score: number;
  playedAt: string;
  username?: string;
}

interface AuthContextType {
  user: User | null;
  login: (username: string, password: string) => Promise<boolean>;
  register: (username: string, email: string, password: string) => Promise<boolean>;
  logout: () => void;
  updateHighScore: (score: number) => Promise<void>;
  saveGameHistory: (score: number) => Promise<void>;
  getLeaderboard: () => Promise<User[]>;
  getGameHistory: () => Promise<GameHistory[]>;
  isLoading: boolean;
  error: string | null;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export const useAuth = () => {
  const context = useContext(AuthContext);
  if (context === undefined) {
    throw new Error('useAuth must be used within an AuthProvider');
  }
  return context;
};

export const AuthProvider: React.FC<{ children: React.ReactNode }> = ({ children }) => {
  const [user, setUser] = useState<User | null>(null);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const token = localStorage.getItem('token');
    if (token) {
      const decodedToken: any = jwtDecode(token);
      if (decodedToken.exp * 1000 > Date.now()) {
        api.defaults.headers.common['Authorization'] = `Bearer ${token}`;
        api.get('/users/me').then(response => {
          setUser(response.data);
        });
      } else {
        logout();
      }
    }
    setIsLoading(false);
  }, []);

  const login = async (username: string, password: string): Promise<boolean> => {
    setIsLoading(true);
    setError(null);
    try {
      const response = await api.post('/auth/login', { username, password });
      const { token, userId } = response.data;
      localStorage.setItem('token', token);
      api.defaults.headers.common['Authorization'] = `Bearer ${token}`;
      const userResponse = await api.get(`/users/${userId}`);
      setUser(userResponse.data);
      return true;
    } catch (err: any) {
      setError(err.response?.data?.message || 'Login failed');
      return false;
    } finally {
      setIsLoading(false);
    }
  };

  const register = async (username: string, email: string, password: string): Promise<boolean> => {
    setIsLoading(true);
    setError(null);
    try {
      await api.post('/auth/register', { username, email, password });
      return await login(username, password);
    } catch (err: any) {
      setError(err.response?.data?.message || 'Registration failed');
      return false;
    } finally {
      setIsLoading(false);
    }
  };

  const logout = () => {
    setUser(null);
    localStorage.removeItem('token');
    delete api.defaults.headers.common['Authorization'];
  };

  const updateHighScore = async (score: number) => {
    if (user && score > user.highestScore) {
      // API call to update high score
    }
  };

  const saveGameHistory = async (score: number) => {
    if (user) {
      try {
        await api.post('/gamehistory', { score });
        const userResponse = await api.get('/users/me');
        setUser(userResponse.data);
      } catch (err) {
        console.error("Failed to save game history:", err);
      }
    }
  };

  const getLeaderboard = async (): Promise<User[]> => {
    try {
      const response = await api.get('/users/leaderboard');
      return response.data;
    } catch (err) {
      console.error("Failed to fetch leaderboard:", err);
      return [];
    }
  };

  const getGameHistory = async (): Promise<GameHistory[]> => {
    try {
      const response = await api.get('/gamehistory/user/me');
      return response.data;
    } catch (err) {
      console.error("Failed to fetch game history:", err);
      return [];
    }
  };

  const value = {
    user,
    login,
    register,
    logout,
    updateHighScore,
    saveGameHistory,
    getLeaderboard,
    getGameHistory,
    isLoading,
    error
  };

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
};