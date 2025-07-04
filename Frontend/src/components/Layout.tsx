import React, { useState } from 'react';
import { useAuth } from '../contexts/AuthContext';
import AuthForm from './AuthForm';
import LeaderboardModal from './LeaderboardModal';
import { Button } from './ui/button';
import { Trophy, User, LogOut, Play, Menu, X } from 'lucide-react';

interface LayoutProps {
  children: React.ReactNode;
  currentPage: 'game' | 'leaderboard';
  onPageChange: (page: 'game' | 'leaderboard') => void;
}

const Layout: React.FC<LayoutProps> = ({ children, currentPage, onPageChange }) => {
  const { user, logout } = useAuth();
  const [showAuthForm, setShowAuthForm] = useState(false);
  const [showLeaderboard, setShowLeaderboard] = useState(false);
  const [mobileMenuOpen, setMobileMenuOpen] = useState(false);

  return (
    <div className="min-h-screen light-bg">
      {/* Navigation */}
      <nav className="relative z-10">
        <div className="glass-card rounded-none border-0 border-b border-gray-200/50">
          <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
            <div className="flex justify-between items-center h-16">
              {/* Logo */}
              <div className="flex items-center">
                <h1 className="text-xl sm:text-2xl font-bold text-rainbow">
                  Myth Or Fact LGBTQIA+
                </h1>
              </div>

              {/* Desktop Navigation */}
              <div className="hidden md:flex items-center space-x-4">
                <Button
                  variant="ghost"
                  onClick={() => onPageChange('game')}
                  className={`text-gray-700 hover:bg-gray-100/50 font-medium ${
                    currentPage === 'game' ? 'bg-gray-100/70 text-gray-900' : ''
                  }`}
                >
                  <Play className="w-4 h-4 mr-2" />
                  Play
                </Button>
                <Button
                  variant="ghost"
                  onClick={() => setShowLeaderboard(true)}
                  className="text-gray-700 hover:bg-gray-100/50 font-medium"
                >
                  <Trophy className="w-4 h-4 mr-2" />
                  Leaderboard
                </Button>
              </div>

              {/* Desktop User Section */}
              <div className="hidden md:flex items-center space-x-4">
                {user ? (
                  <div className="flex items-center space-x-3">
                    <div className="text-right">
                      <div className="text-gray-900 font-semibold">
                        {user.username}
                      </div>
                      <div className="text-gray-600 text-sm">
                        Best: {user.highestScore}
                      </div>
                    </div>
                    <Button
                      variant="ghost"
                      onClick={logout}
                      className="text-gray-700 hover:bg-gray-100/50"
                    >
                      <LogOut className="w-4 h-4" />
                    </Button>
                  </div>
                ) : (
                  <Button
                    onClick={() => setShowAuthForm(true)}
                    className="rainbow-border text-gray-700 font-semibold btn-hover"
                  >
                    <User className="w-4 h-4 mr-2" />
                    Sign In
                  </Button>
                )}
              </div>

              {/* Mobile menu button */}
              <div className="md:hidden">
                <Button
                  variant="ghost"
                  onClick={() => setMobileMenuOpen(!mobileMenuOpen)}
                  className="text-gray-700 hover:bg-gray-100/50"
                >
                  {mobileMenuOpen ? <X className="w-5 h-5" /> : <Menu className="w-5 h-5" />}
                </Button>
              </div>
            </div>

            {/* Mobile Navigation Menu */}
            {mobileMenuOpen && (
              <div className="md:hidden border-t border-gray-200/50 py-4">
                <div className="flex flex-col space-y-3">
                  <Button
                    variant="ghost"
                    onClick={() => {
                      onPageChange('game');
                      setMobileMenuOpen(false);
                    }}
                    className={`justify-start text-gray-700 hover:bg-gray-100/50 font-medium ${
                      currentPage === 'game' ? 'bg-gray-100/70 text-gray-900' : ''
                    }`}
                  >
                    <Play className="w-4 h-4 mr-2" />
                    Play
                  </Button>
                  <Button
                    variant="ghost"
                    onClick={() => {
                      setShowLeaderboard(true);
                      setMobileMenuOpen(false);
                    }}
                    className="justify-start text-gray-700 hover:bg-gray-100/50 font-medium"
                  >
                    <Trophy className="w-4 h-4 mr-2" />
                    Leaderboard
                  </Button>

                  {user ? (
                    <div className="pt-3 border-t border-gray-200/50">
                      <div className="px-3 mb-3">
                        <div className="text-gray-900 font-semibold">
                          {user.username}
                        </div>
                        <div className="text-gray-600 text-sm">
                          Best: {user.highest_score}
                        </div>
                      </div>
                      <Button
                        variant="ghost"
                        onClick={() => {
                          logout();
                          setMobileMenuOpen(false);
                        }}
                        className="justify-start text-gray-700 hover:bg-gray-100/50 w-full"
                      >
                        <LogOut className="w-4 h-4 mr-2" />
                        Sign Out
                      </Button>
                    </div>
                  ) : (
                    <div className="pt-3 border-t border-gray-200/50">
                      <Button
                        onClick={() => {
                          setShowAuthForm(true);
                          setMobileMenuOpen(false);
                        }}
                        className="pride-gradient text-white font-semibold btn-hover w-full"
                      >
                        <User className="w-4 h-4 mr-2" />
                        Sign In
                      </Button>
                    </div>
                  )}
                </div>
              </div>
            )}
          </div>
        </div>
      </nav>

      {/* Main Content */}
      <main>{children}</main>

      {/* Auth Modal */}
      {showAuthForm && (
        <div
          className="fixed inset-0 z-50 bg-black/50"
          onClick={() => setShowAuthForm(false)}
        >
          <div onClick={(e) => e.stopPropagation()}>
            <AuthForm onClose={() => setShowAuthForm(false)} />
          </div>
        </div>
      )}

      {/* Leaderboard Modal */}
      {showLeaderboard && (
        <div
          className="fixed inset-0 z-50 bg-black/50"
          onClick={() => setShowLeaderboard(false)}
        >
          <div onClick={(e) => e.stopPropagation()}>
            <LeaderboardModal onClose={() => setShowLeaderboard(false)} />
          </div>
        </div>
      )}
    </div>
  );
};

export default Layout;
