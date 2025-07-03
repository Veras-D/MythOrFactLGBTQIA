import React, { useState } from 'react';
import { useAuth } from '../contexts/AuthContext';
import { Button } from './ui/button';
import { Input } from './ui/input';
import { Card } from './ui/card';
import { toast } from 'sonner';

interface AuthFormProps {
  onClose?: () => void;
}

const AuthForm: React.FC<AuthFormProps> = ({ onClose }) => {
  const [isLogin, setIsLogin] = useState(true);
  const [formData, setFormData] = useState({
    username: '',
    email: '',
    password: '',
    confirmPassword: ''
  });
  const [isSubmitting, setIsSubmitting] = useState(false);

  const { login, register, error } = useAuth();

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setIsSubmitting(true);

    if (!isLogin && formData.password !== formData.confirmPassword) {
      toast.error('Passwords do not match');
      setIsSubmitting(false);
      return;
    }

    try {
      let success = false;
      if (isLogin) {
        success = await login(formData.username, formData.password);
      } else {
        success = await register(formData.username, formData.email, formData.password);
      }

      if (success) {
        toast.success(isLogin ? 'Welcome back!' : 'Account created successfully!');
        onClose?.();
      } else {
        toast.error(error || 'Authentication failed');
      }
    } catch (err) {
      toast.error('An error occurred');
    } finally {
      setIsSubmitting(false);
    }
  };

  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setFormData(prev => ({
      ...prev,
      [e.target.name]: e.target.value
    }));
  };

  return (
    <div className="fixed inset-0 bg-black/20 backdrop-blur-sm flex items-center justify-center p-4 z-50">
      <Card className="w-full max-w-md glass-card border-0 p-8">
        <div className="text-center mb-6">
          <h2 className="text-3xl font-bold text-rainbow mb-2">
            {isLogin ? 'Welcome Back!' : 'Join the Game!'}
          </h2>
          <p className="text-gray-600">
            {isLogin ? 'Sign in to continue your journey' : 'Create an account to start playing'}
          </p>
        </div>

        <form onSubmit={handleSubmit} className="space-y-4">
          <div>
            <label className="block text-sm font-semibold text-gray-800 mb-2">
              Username
            </label>
            <Input
              name="username"
              type="text"
              placeholder="Enter your username"
              value={formData.username}
              onChange={handleInputChange}
              required
              className="glass border-gray-300 text-gray-900 placeholder-gray-500 focus:border-blue-500"
            />
          </div>

          {!isLogin && (
            <div>
              <label className="block text-sm font-semibold text-gray-800 mb-2">
                Email
              </label>
              <Input
                name="email"
                type="email"
                placeholder="Enter your email"
                value={formData.email}
                onChange={handleInputChange}
                required
                className="glass border-gray-300 text-gray-900 placeholder-gray-500 focus:border-blue-500"
              />
            </div>
          )}

          <div>
            <label className="block text-sm font-semibold text-gray-800 mb-2">
              Password
            </label>
            <Input
              name="password"
              type="password"
              placeholder="Enter your password"
              value={formData.password}
              onChange={handleInputChange}
              required
              className="glass border-gray-300 text-gray-900 placeholder-gray-500 focus:border-blue-500"
            />
          </div>

          {!isLogin && (
            <div>
              <label className="block text-sm font-semibold text-gray-800 mb-2">
                Confirm Password
              </label>
              <Input
                name="confirmPassword"
                type="password"
                placeholder="Confirm your password"
                value={formData.confirmPassword}
                onChange={handleInputChange}
                required
                className="glass border-gray-300 text-gray-900 placeholder-gray-500 focus:border-blue-500"
              />
            </div>
          )}

          <Button
            type="submit"
            disabled={isSubmitting}
            className="w-full pride-gradient text-white font-semibold py-3 btn-hover"
          >
            {isSubmitting ? 'Please wait...' : (isLogin ? 'Sign In' : 'Create Account')}
          </Button>
        </form>

        <div className="mt-6 text-center">
          <button
            type="button"
            onClick={() => setIsLogin(!isLogin)}
            className="text-gray-700 hover:text-gray-900 transition-colors underline font-medium"
          >
            {isLogin ? "Don't have an account? Sign up" : 'Already have an account? Sign in'}
          </button>
        </div>

        {onClose && (
          <button
            onClick={onClose}
            className="absolute top-4 right-4 text-gray-500 hover:text-gray-700 transition-colors text-2xl"
          >
            ×
          </button>
        )}
      </Card>
    </div>
  );
};

export default AuthForm;
