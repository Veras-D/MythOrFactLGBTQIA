import React, { useState } from 'react';
import { Card } from '../components/ui/card';
import { Input } from '../components/ui/input';
import { Button } from '../components/ui/button';
import { toast } from 'sonner';
import api from '../lib/api';
import { Mail, ArrowLeft } from 'lucide-react';
import { Link } from 'react-router-dom';

const ForgotPassword = () => {
  const [email, setEmail] = useState('');
  const [message, setMessage] = useState('');
  const [isSubmitting, setIsSubmitting] = useState(false);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setIsSubmitting(true);
    setMessage('');

    try {
      await api.post('/auth/forgot-password', { email });
      setMessage('If an account with that email exists, a password reset link has been sent.');
      toast.success('Password reset request sent!');
    } catch (err: unknown) {
      const errorMessage = err.response?.data || 'Failed to send password reset request.';
      setMessage(errorMessage);
      toast.error(errorMessage);
    } finally {
      setIsSubmitting(false);
    }
  };

  return (
    <div className="min-h-screen flex items-center justify-center light-bg p-4">
      <Card className="w-full max-w-md glass-card border-0 p-8 text-center mx-4">
        <div className="mb-6">
          <Mail className="w-16 h-16 text-blue-500 mx-auto mb-4 float" />
          <h2 className="text-2xl font-bold text-gray-900 mb-2">Forgot Password?</h2>
          <p className="text-gray-700">Enter your email address and we'll send you a link to reset your password.</p>
        </div>

        <form onSubmit={handleSubmit} className="space-y-4">
          <div>
            <label htmlFor="email" className="sr-only">Email</label>
            <Input
              id="email"
              type="email"
              placeholder="Enter your email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              required
              className="glass border-gray-300 text-gray-900 placeholder-gray-500 focus:border-blue-500"
            />
          </div>

          <Button
            type="submit"
            disabled={isSubmitting}
            className="w-full pride-gradient text-white font-semibold py-3 btn-hover"
          >
            {isSubmitting ? 'Sending...' : 'Send Reset Link'}
          </Button>
        </form>

        {message && (
          <div className="mt-4 text-sm text-gray-600">
            {message}
          </div>
        )}

        <div className="mt-6">
          <Link to="/">
            <Button
              variant="outline"
              className="w-full font-semibold hover:bg-gray-50"
            >
              <ArrowLeft className="w-4 h-4 mr-2" />
              Back to Login
            </Button>
          </Link>
        </div>
      </Card>
    </div>
  );
};

export default ForgotPassword;
