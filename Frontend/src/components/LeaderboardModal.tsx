import React, { useState, useEffect } from 'react';
import { useAuth, User } from '../contexts/AuthContext';
import { Card } from './ui/card';
import { Badge } from './ui/badge';
import { Trophy, Medal, Award, X } from 'lucide-react';

interface LeaderboardModalProps {
  onClose: () => void;
}

const LeaderboardModal: React.FC<LeaderboardModalProps> = ({ onClose }) => {
  const { getLeaderboard, user } = useAuth();
  const [leaderboard, setLeaderboard] = useState<User[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchLeaderboard = async () => {
      setLoading(true);
      try {
        const data = await getLeaderboard();
        setLeaderboard(data);
      } catch (error) {
        console.error("Failed to fetch leaderboard:", error);
      } finally {
        setLoading(false);
      }
    };

    fetchLeaderboard();
  }, [getLeaderboard]);

  const getRankIcon = (rank: number) => {
    switch (rank) {
      case 1:
        return <Trophy className="w-6 h-6 text-yellow-500" />;
      case 2:
        return <Medal className="w-6 h-6 text-gray-400" />;
      case 3:
        return <Award className="w-6 h-6 text-amber-600" />;
      default:
        return <span className="w-6 h-6 flex items-center justify-center text-gray-700 font-bold">{rank}</span>;
    }
  };

  const formatDate = (dateString: string) => {
    return new Date(dateString).toLocaleDateString('en-US', {
      month: 'short',
      day: 'numeric',
      year: 'numeric'
    });
  };

  const userRank = leaderboard.findIndex(p => p.id === user?.id) + 1;

  return (
    <div className="fixed inset-0 bg-black/20 backdrop-blur-sm flex items-center justify-center p-4 z-50">
      <Card className="w-full max-w-2xl glass-card border-0 p-6 max-h-[80vh] overflow-y-auto">
        <div className="flex items-center justify-between mb-6">
          <div className="flex items-center gap-2">
            <Trophy className="w-8 h-8 text-yellow-500" />
            <h2 className="text-2xl font-bold text-gray-900">Global Leaderboard</h2>
          </div>
          <button
            onClick={onClose}
            className="text-gray-500 hover:text-gray-700 transition-colors"
          >
            <X className="w-6 h-6" />
          </button>
        </div>

        {loading ? (
          <div className="text-center py-8 text-gray-500">Loading...</div>
        ) : (
          <>
            {user && userRank > 0 && (
              <div className="mb-6 p-4 bg-blue-50 rounded-lg border border-blue-200">
                <div className="text-center">
                  <div className="text-sm text-gray-600 mb-1">Your Position</div>
                  <div className="text-2xl font-bold text-blue-600">#{userRank}</div>
                  <div className="text-sm text-gray-600">
                    Score: {user.highest_score} points
                  </div>
                </div>
              </div>
            )}

            <div className="space-y-3">
              {leaderboard.map((player, index) => (
                <div
                  key={player.id}
                  className={`flex items-center gap-4 p-4 rounded-lg transition-all ${
                    user?.id === player.id
                      ? 'bg-blue-50 border-2 border-blue-300'
                      : 'bg-gray-50/70 hover:bg-gray-100/70'
                  }`}
                >
                  <div className="flex-shrink-0">
                    {getRankIcon(index + 1)}
                  </div>
                  <div className="flex-grow">
                    <div className="flex items-center gap-2">
                      <span className="text-gray-900 font-semibold text-lg">
                        {player.username}
                      </span>
                      {user?.id === player.id && (
                        <Badge className="bg-blue-100 text-blue-700 text-xs">
                          You
                        </Badge>
                      )}
                    </div>
                    <div className="text-gray-500 text-sm">
                      Joined {formatDate(player.created_at)}
                    </div>
                  </div>
                  <div className="flex-shrink-0 text-right">
                    <div className="text-2xl font-bold text-rainbow">
                      {player.highest_score}
                    </div>
                    <div className="text-gray-500 text-xs">
                      points
                    </div>
                  </div>
                </div>
              ))}

              {leaderboard.length === 0 && (
                <div className="text-center py-8 text-gray-500">
                  <Trophy className="w-12 h-12 mx-auto mb-4 opacity-50" />
                  <p>No scores yet! Be the first to play!</p>
                </div>
              )}
            </div>
          </>
        )}
      </Card>
    </div>
  );
};

export default LeaderboardModal;