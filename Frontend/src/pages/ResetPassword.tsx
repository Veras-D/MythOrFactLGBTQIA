import React, { useState, useEffect } from 'react';
import { Card } from '../components/ui/card';
import { Input } from '../components/ui/input';
import { Button } from '../components/ui/button';
import { toast } from 'sonner';
import api from '../lib/api';
import { useSearchParams, Link } from 'react-router-dom';
import { CheckCircle, XCircle, Loader2, ArrowLeft, Eye, EyeOff } from 'lucide-react';

const ResetPassword = () => {
  const [searchParams] = useSearchParams();
  const token = searchParams.get('token');

  const [newPassword, setNewPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [showNewPassword, setShowNewPassword] = useState(false);
  const [showConfirmPassword, setShowConfirmPassword] = useState(false);
  const [message, setMessage] = useState('');
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [isValidToken, setIsValidToken] = useState<boolean | null>(null);
  const [isSuccess, setIsSuccess] = useState(false);

  useEffect(() => {
    if (!token) {
      setIsValidToken(false);
      setMessage('No reset token provided.');
    } else {
      setIsValidToken(true);
    }
  }, [token]);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setIsSubmitting(true);
    setMessage('');

    if (!token) {
      setMessage('No reset token found. Please use the link from your email.');
      setIsSubmitting(false);
      return;
    }

    if (newPassword !== confirmPassword) {
      toast.error('Passwords do not match.');
      setIsSubmitting(false);
      return;
    }

    if (newPassword.length < 8) {
      toast.error('Password must be at least 8 characters long.');
      setIsSubmitting(false);
      return;
    }

    if (!/[a-zA-Z]/.test(newPassword)) {
      toast.error('Password must contain at least one letter.');
      setIsSubmitting(false);
      return;
    }

    if (!/[0-9]/.test(newPassword)) {
      toast.error('Password must contain at least one number.');
      setIsSubmitting(false);
      return;
    }

    try {
      await api.post('/auth/reset-password', { token, newPassword });
      toast.success('Your password has been reset successfully!');
      setIsSuccess(true);
    } catch (err: unknown) {
      const errorMessage = err.response?.data || 'Failed to reset password.';
      toast.error(errorMessage);
      setIsValidToken(false);
    } finally {
      setIsSubmitting(false);
    }
  };

  if (isValidToken === null) {
    return (
      <div className="min-h-screen flex items-center justify-center light-bg p-4">
        <Card className="w-full max-w-md glass-card border-0 p-8 text-center mx-4">
          <Loader2 className="w-16 h-16 text-blue-500 mx-auto mb-4 animate-spin" />
          <h2 className="text-2xl font-bold text-gray-900 mb-2">Loading...</h2>
        </Card>
      </div>
    );
  }

  if (isSuccess) {
    return (
      <div className="min-h-screen flex items-center justify-center light-bg p-4">
        <Card className="w-full max-w-md glass-card border-0 p-8 text-center mx-4">
          <CheckCircle className="w-16 h-16 text-green-500 mx-auto mb-4" />
          <h2 className="text-2xl font-bold text-gray-900 mb-2">Password Reset Successful!</h2>
          <p className="text-gray-700">You can now log in with your new password.</p>
          <div className="mt-6">
            <Link to="/">
              <Button className="w-full pride-gradient text-white font-semibold py-3 btn-hover">
                Back to Login
              </Button>
            </Link>
          </div>
        </Card>
      </div>
    );
  }

  if (isValidToken === false) {
    return (
      <div className="min-h-screen flex items-center justify-center light-bg p-4">
        <Card className="w-full max-w-md glass-card border-0 p-8 text-center mx-4">
          <XCircle className="w-16 h-16 text-red-500 mx-auto mb-4" />
          <h2 className="text-2xl font-bold text-gray-900 mb-2">Invalid or Missing Token</h2>
          <p className="text-gray-700">{'The password reset link is invalid or has expired.'}</p>
          <div className="mt-6">
            <Link to="/forgot-password">
              <Button className="w-full pride-gradient text-white font-semibold py-3 btn-hover">
                Request New Link
              </Button>
            </Link>
            <Link to="/" className="block mt-2">
              <Button variant="outline" className="w-full font-semibold hover:bg-gray-50">
                <ArrowLeft className="w-4 h-4 mr-2" /> Back to Login
              </Button>
            </Link>
          </div>
        </Card>
      </div>
    );
  }

  return (
    <div className="min-h-screen flex items-center justify-center light-bg p-4">
      <Card className="w-full max-w-md glass-card border-0 p-8 text-center mx-4">
        <div className="mb-6">
          <h2 className="text-2xl font-bold text-gray-900 mb-2">Reset Your Password</h2>
          <p className="text-gray-700">Enter your new password below.</p>
        </div>

        <form onSubmit={handleSubmit} className="space-y-4">
          <div>
            <label htmlFor="newPassword" className="sr-only">New Password</label>
            <div className="relative">
              <Input
                id="newPassword"
                type={showNewPassword ? "text" : "password"}
                placeholder="New Password"
                value={newPassword}
                onChange={(e) => setNewPassword(e.target.value)}
                required
                className="glass border-gray-300 text-gray-900 placeholder-gray-500 focus:border-blue-500 pr-10"
              />
              <button
                type="button"
                onClick={() => setShowNewPassword(!showNewPassword)}
                className="absolute inset-y-0 right-0 pr-3 flex items-center text-gray-500"
              >
                {showNewPassword ? <EyeOff size={20} /> : <Eye size={20} />}
              </button>
            </div>
          </div>

          <div>
            <label htmlFor="confirmPassword" className="sr-only">Confirm New Password</label>
            <div className="relative">
              <Input
                id="confirmPassword"
                type={showConfirmPassword ? "text" : "password"}
                placeholder="Confirm New Password"
                value={confirmPassword}
                onChange={(e) => setConfirmPassword(e.target.value)}
                required
                className="glass border-gray-300 text-gray-900 placeholder-gray-500 focus:border-blue-500 pr-10"
              />
              <button
                type="button"
                onClick={() => setShowConfirmPassword(!showConfirmPassword)}
                className="absolute inset-y-0 right-0 pr-3 flex items-center text-gray-500"
              >
                {showConfirmPassword ? <EyeOff size={20} /> : <Eye size={20} />}
              </button>
            </div>
          </div>

          <Button
            type="submit"
            disabled={isSubmitting}
            className="w-full pride-gradient text-white font-semibold py-3 btn-hover"
          >
            {isSubmitting ? 'Resetting...' : 'Reset Password'}
          </Button>
        </form>

        <div className="mt-6">
          <Link to="/" className="block">
            <Button variant="outline" className="w-full font-semibold hover:bg-gray-50">
              <ArrowLeft className="w-4 h-4 mr-2" /> Back to Login
            </Button>
          </Link>
        </div>
      </Card>
    </div>
  );
};

export default ResetPassword;
