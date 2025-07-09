import React, { useState } from 'react';
import { useAuth } from '../contexts/AuthContext';
import { Button } from './ui/button';
import { Input } from './ui/input';
import { Card } from './ui/card';
import { toast } from 'sonner';
import { Mail, ArrowLeft } from 'lucide-react';
import { Link } from 'react-router-dom';

interface AuthFormProps {
  onClose?: () => void;
}

const AuthForm: React.FC<AuthFormProps> = ({ onClose }) => {
  const [isLogin, setIsLogin] = useState(true);
  const [showConfirmation, setShowConfirmation] = useState(false);
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

    if (!isLogin) {
      if (formData.password !== formData.confirmPassword) {
        toast.error('Passwords do not match');
        setIsSubmitting(false);
        return;
      }
      if (formData.password.length < 8) {
        toast.error('Password must be at least 8 characters long');
        setIsSubmitting(false);
        return;
      }
      if (!/[a-zA-Z]/.test(formData.password)) {
        toast.error('Password must contain at least one letter');
        setIsSubmitting(false);
        return;
      }
      if (!/[0-9]/.test(formData.password)) {
        toast.error('Password must contain at least one number');
        setIsSubmitting(false);
        return;
      }
    }

    try {
      let success = false;
      let loginResult: boolean | string = false;
      let registerResult: boolean | string = false;

      if (isLogin) {
        loginResult = await login(formData.username, formData.password);
        success = typeof loginResult === 'boolean' ? loginResult : false;
      } else {
        registerResult = await register(formData.username, formData.email, formData.password);
        success = typeof registerResult === 'boolean' ? registerResult : false;
      }

      if (success) {
        if (isLogin) {
          toast.success('Welcome back!');
          onClose?.();
        } else {
          setShowConfirmation(true);
        }
      } else {
        const errorMessage = isLogin ? loginResult : registerResult;
        toast.error(typeof errorMessage === 'string' ? errorMessage : 'Authentication failed');
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

  if (showConfirmation) {
    return (
      <div 
        className="fixed inset-0 z-50 flex items-center justify-center light-bg bg-black/50"
        onClick={onClose}
      >
        <Card 
          className="w-full max-w-md glass-card border-0 p-8 text-center mx-4"
          onClick={(e) => e.stopPropagation()}
        >
          <div className="mb-6">
            <Mail className="w-16 h-16 text-blue-500 mx-auto mb-4 float" />
            <h2 className="text-2xl font-bold text-gray-900 mb-2">
              Confirm your Email
            </h2>
            <p className="text-gray-700">
              We sent a confirmation link to:
            </p>
            <p className="text-blue-600 font-semibold mt-2 break-all">
              {formData.email}
            </p>
          </div>

          <div className="space-y-4 text-gray-600">
            <p className="text-sm">
              Click the link in the email to confirm your account and log in.
            </p>
            <p className="text-sm">
              Didn't receive the email? Check your spam folder.
            </p>
          </div>

          <div className="mt-6">
            <Button
              onClick={onClose}
              variant="outline"
              className="w-full font-semibold hover:bg-gray-50"
            >
              <ArrowLeft className="w-4 h-4 mr-2" />
              Back to Login
            </Button>
          </div>
        </Card>
      </div>
    );
  }

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
            {isLogin && (
              <div className="text-right pt-1">
                <Link to="/forgot-password" className="text-sm text-gray-600 hover:text-gray-800 hover:underline" onClick={onClose}>
                  Forgot Password?
                </Link>
              </div>
            )}
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
