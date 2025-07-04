import React, { createContext, useContext, useState, useEffect } from 'react';

export interface User {
  id: number;
  username: string;
  email: string;
  highest_score: number;
  created_at: string;
}

export interface GameHistory {
  id: number;
  user_id: number;
  score: number;
  played_at: string;
  username?: string;
}

interface AuthContextType {
  user: User | null;
  login: (email: string, password: string) => Promise<boolean>;
  register: (username: string, email: string, password: string) => Promise<boolean>;
  logout: () => void;
  updateHighScore: (score: number) => void;
  saveGameHistory: (score: number) => void;
  getLeaderboard: () => User[];
  getGameHistory: () => GameHistory[];
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

  // Mock data storage (simulating database)
  const [users, setUsers] = useState<User[]>(() => {
    const savedUsers = localStorage.getItem('lgbtmythorfact_users');
    return savedUsers ? JSON.parse(savedUsers) : [];
  });

  const [gameHistory, setGameHistory] = useState<GameHistory[]>(() => {
    const savedHistory = localStorage.getItem('lgbtmythorfact_history');
    return savedHistory ? JSON.parse(savedHistory) : [];
  });

  useEffect(() => {
    // Check if user is already logged in
    const savedUser = localStorage.getItem('lgbtmythorfact_user');
    if (savedUser) {
      const userData = JSON.parse(savedUser);
      // Find the latest user data
      const currentUser = users.find(u => u.id === userData.id);
      setUser(currentUser || userData);
    }
    setIsLoading(false);
  }, [users]);

  useEffect(() => {
    localStorage.setItem('lgbtmythorfact_users', JSON.stringify(users));
  }, [users]);

  useEffect(() => {
    localStorage.setItem('lgbtmythorfact_history', JSON.stringify(gameHistory));
  }, [gameHistory]);

  const login = async (email: string, password: string): Promise<boolean> => {
    setIsLoading(true);
    setError(null);

    try {
      // Simulate API call delay
      await new Promise(resolve => setTimeout(resolve, 1000));

      const foundUser = users.find(u => u.email === email);
      if (!foundUser) {
        setError('User not found');
        return false;
      }

      if (foundUser.email === email) {
        setUser(foundUser);
        localStorage.setItem('lgbtmythorfact_user', JSON.stringify(foundUser));
        return true;
      } else {
        setError('Invalid credentials');
        return false;
      }
    } catch (err) {
      setError('Login failed');
      return false;
    } finally {
      setIsLoading(false);
    }
  };

  const register = async (username: string, email: string, password: string): Promise<boolean> => {
    setIsLoading(true);
    setError(null);

    try {
      // Simulate API call delay
      await new Promise(resolve => setTimeout(resolve, 1000));

      // Check if user already exists
      if (users.some(u => u.email === email)) {
        setError('User with this email already exists');
        return false;
      }

      if (users.some(u => u.username === username)) {
        setError('Username already taken');
        return false;
      }

      const newUser: User = {
        id: Date.now(), // Simple ID generation for demo
        username,
        email,
        highest_score: 0,
        created_at: new Date().toISOString()
      };

      setUsers(prev => [...prev, newUser]);
      setUser(newUser);
      localStorage.setItem('lgbtmythorfact_user', JSON.stringify(newUser));
      return true;
    } catch (err) {
      setError('Registration failed');
      return false;
    } finally {
      setIsLoading(false);
    }
  };

  const logout = () => {
    setUser(null);
    localStorage.removeItem('lgbtmythorfact_user');
  };

  const updateHighScore = (score: number) => {
    if (user && score > user.highest_score) {
      const updatedUser = { ...user, highest_score: score };
      setUser(updatedUser);
      setUsers(prev => prev.map(u => u.id === user.id ? updatedUser : u));
      localStorage.setItem('lgbtmythorfact_user', JSON.stringify(updatedUser));
    }
  };

  const saveGameHistory = (score: number) => {
    if (user) {
      const newGame: GameHistory = {
        id: Date.now(),
        user_id: user.id,
        score,
        played_at: new Date().toISOString(),
        username: user.username
      };
      setGameHistory(prev => [...prev, newGame]);
    }
  };

  const getLeaderboard = (): User[] => {
    return [...users]
      .sort((a, b) => b.highest_score - a.highest_score)
      .slice(0, 10);
  };

  const getGameHistory = (): GameHistory[] => {
    return gameHistory
      .map(game => ({
        ...game,
        username: users.find(u => u.id === game.user_id)?.username || 'Unknown'
      }))
      .sort((a, b) => new Date(b.played_at).getTime() - new Date(a.played_at).getTime())
      .slice(0, 20);
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