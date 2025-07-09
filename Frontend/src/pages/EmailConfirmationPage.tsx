import React, { useEffect, useState } from 'react';
import { useSearchParams, Link } from 'react-router-dom';
import { useAuth } from '../contexts/AuthContext';
import { Card } from '../components/ui/card';
import { Button } from '../components/ui/button';
import { CheckCircle, XCircle, Loader2, ArrowLeft } from 'lucide-react';

const EmailConfirmationPage: React.FC = () => {
  const [searchParams] = useSearchParams();
  const token = searchParams.get('token');
  const { confirmEmail, error, isLoading } = useAuth();
  const [confirmationStatus, setConfirmationStatus] = useState<'idle' | 'loading' | 'success' | 'error'>('idle');

  useEffect(() => {
    if (token && confirmationStatus === 'idle') {
      setConfirmationStatus('loading');
      const confirm = async () => {
        const success = await confirmEmail(token);
        if (success) {
          setConfirmationStatus('success');
        } else {
          setConfirmationStatus('error');
        }
      };
      confirm();
    } else if (!token) {
      setConfirmationStatus('error'); // No token provided
    }
  }, [token, confirmEmail, confirmationStatus]);

  const renderContent = () => {
    switch (confirmationStatus) {
      case 'loading':
        return (
          <div className="flex flex-col items-center">
            <Loader2 className="w-16 h-16 text-blue-500 animate-spin mb-4" />
            <h2 className="text-2xl font-bold text-gray-900 mb-2">Confirming your email...</h2>
            <p className="text-gray-700">Please wait, this may take a moment.</p>
          </div>
        );
      case 'success':
        return (
          <div className="flex flex-col items-center">
            <CheckCircle className="w-16 h-16 text-green-500 mb-4" />
            <h2 className="text-2xl font-bold text-gray-900 mb-2">Email Confirmed!</h2>
            <p className="text-gray-700">Your account has been successfully activated.</p>
            <Link to="/">
              <Button className="mt-6 pride-gradient text-white font-semibold">
                <ArrowLeft className="w-4 h-4 mr-2" /> Go to Login
              </Button>
            </Link>
          </div>
        );
      case 'error':
        return (
          <div className="flex flex-col items-center">
            <XCircle className="w-16 h-16 text-red-500 mb-4" />
            <h2 className="text-2xl font-bold text-gray-900 mb-2">Confirmation Failed</h2>
            <p className="text-gray-700">{error || 'Invalid or expired confirmation link.'}</p>
            <Link to="/">
              <Button className="mt-6 pride-gradient text-white font-semibold">
                <ArrowLeft className="w-4 h-4 mr-2" /> Go to Login
              </Button>
            </Link>
          </div>
        );
      default:
        return (
          <div className="flex flex-col items-center">
            <XCircle className="w-16 h-16 text-red-500 mb-4" />
            <h2 className="text-2xl font-bold text-gray-900 mb-2">Invalid Link</h2>
            <p className="text-gray-700">No confirmation token provided.</p>
            <Link to="/">
              <Button className="mt-6 pride-gradient text-white font-semibold">
                <ArrowLeft className="w-4 h-4 mr-2" /> Go to Login
              </Button>
            </Link>
          </div>
        );
    }
  };

  return (
    <div className="min-h-screen flex items-center justify-center light-bg p-4">
      <Card className="w-full max-w-md glass-card border-0 p-8 text-center mx-4">
        {renderContent()}
      </Card>
    </div>
  );
};

export default EmailConfirmationPage;
