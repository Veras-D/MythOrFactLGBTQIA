import React from 'react';
import { useAuth } from '../contexts/AuthContext';
import api from '../lib/api';
import { Card } from './ui/card';
import { Button } from './ui/button';
import { User as UserIcon, Mail, Trophy, Calendar, Trash2, X } from 'lucide-react';
import { toast } from 'sonner';
import {
  AlertDialog,
  AlertDialogAction,
  AlertDialogCancel,
  AlertDialogContent,
  AlertDialogDescription,
  AlertDialogFooter,
  AlertDialogHeader,
  AlertDialogTitle,
  AlertDialogTrigger,
} from "./ui/alert-dialog";

interface ProfileModalProps {
  onClose: () => void;
}

const ProfileModal: React.FC<ProfileModalProps> = ({ onClose }) => {
  const { user, logout } = useAuth();

  if (!user) {
    return null;
  }

  const formatDate = (dateString: string) => {
    return new Date(dateString).toLocaleDateString('en-US', {
      year: 'numeric',
      month: 'long',
      day: 'numeric',
    });
  };

  const handleDeleteAccount = async () => {
    try {
      await api.delete('/users/me');
      toast.success('Account deleted successfully!');
      logout();
    } catch (error) {
      console.error("Failed to delete account:", error);
      toast.error('Failed to delete account. Please try again.');
    }
  };

  return (
    <div
      className="fixed inset-0 z-50 flex items-center justify-center light-bg bg-black/50"
      onClick={onClose}
    >
      <Card
        className="w-full max-w-md glass-card border-0 p-8 text-center mx-4"
        onClick={(e) => e.stopPropagation()}
      >
        <div className="flex items-center justify-between mb-6">
          <div className="flex items-center gap-2">
            <UserIcon className="w-8 h-8 text-blue-500" />
            <h2 className="text-2xl font-bold text-gray-900">Your Profile</h2>
          </div>
          <button
            onClick={onClose}
            className="text-gray-500 hover:text-gray-700 transition-colors"
          >
            <X className="w-6 h-6" />
          </button>
        </div>

        <div className="space-y-4 text-left">
          <div className="flex items-center">
            <Mail className="w-5 h-5 text-gray-600 mr-3" />
            <span className="text-gray-800 font-medium">Email:</span>
            <span className="ml-auto text-gray-700">{user.email}</span>
          </div>
          <div className="flex items-center">
            <Trophy className="w-5 h-5 text-gray-600 mr-3" />
            <span className="text-gray-800 font-medium">Highest Score:</span>
            <span className="ml-auto text-gray-700">{user.highestScore}</span>
          </div>
          <div className="flex items-center">
            <Calendar className="w-5 h-5 text-gray-600 mr-3" />
            <span className="text-gray-800 font-medium">Member Since:</span>
            <span className="ml-auto text-gray-700">{formatDate(user.createdAt)}</span>
          </div>
        </div>

        <div className="mt-8 space-y-4">
          <Button
            onClick={logout}
            className="w-full rainbow-border text-gray-600 font-semibold py-3 btn-hover"
          >
            Logout
          </Button>

          <AlertDialog>
            <AlertDialogTrigger asChild>
              <Button
                variant="outline"
                className="w-full border-red-500 text-red-500 hover:bg-red-50 hover:text-red-600 font-semibold py-3"
              >
                <Trash2 className="w-4 h-4 mr-2" />
                Delete Account
              </Button>
            </AlertDialogTrigger>
            <AlertDialogContent>
              <AlertDialogHeader>
                <AlertDialogTitle>Are you absolutely sure?</AlertDialogTitle>
                <AlertDialogDescription>
                  This action cannot be undone. This will permanently delete your
                  account and remove your data from our servers.
                </AlertDialogDescription>
              </AlertDialogHeader>
              <AlertDialogFooter>
                <AlertDialogCancel>Cancel</AlertDialogCancel>
                <AlertDialogAction onClick={handleDeleteAccount} className="bg-red-500 hover:bg-red-600 text-white">
                  Delete
                </AlertDialogAction>
              </AlertDialogFooter>
            </AlertDialogContent>
          </AlertDialog>
        </div>
      </Card>
    </div>
  );
};

export default ProfileModal;