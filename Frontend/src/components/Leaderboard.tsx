import React, { useState, useEffect } from 'react';
import { useAuth, User, GameHistory } from '../contexts/AuthContext';
import { Card } from './ui/card';
import { Badge } from './ui/badge';
import { Trophy, Medal, Award, Clock } from 'lucide-react';

const Leaderboard: React.FC = () => {
  const { getLeaderboard, getGameHistory, user } = useAuth();
  const [leaderboard, setLeaderboard] = useState<User[]>([]);
  const [recentGames, setRecentGames] = useState<GameHistory[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchData = async () => {
      setLoading(true);
      try {
        const [leaderboardData, historyData] = await Promise.all([
          getLeaderboard(),
          getGameHistory(),
        ]);
        setLeaderboard(leaderboardData);
        setRecentGames(historyData);
      } catch (error) {
        console.error("Failed to fetch leaderboard data:", error);
      } finally {
        setLoading(false);
      }
    };

    fetchData();
  }, [getLeaderboard, getGameHistory]);

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
      hour: '2-digit',
      minute: '2-digit'
    });
  };

  if (loading) {
    return (
      <div className="min-h-screen light-bg flex items-center justify-center">
        <div className="text-gray-900 text-xl">Loading Leaderboard...</div>
      </div>
    );
  }

  return (
    <div className="min-h-screen light-bg p-4">
      <div className="max-w-6xl mx-auto">
        <div className="text-center mb-8">
          <h1 className="text-5xl font-bold text-gray-900 mb-4">
            <span className="text-rainbow">Leaderboard</span>
          </h1>
          <p className="text-gray-600 text-xl">See who knows LGBT+ facts the best!</p>
        </div>

        <div className="grid grid-cols-1 lg:grid-cols-2 gap-8">
          {/* Top Scores */}
          <Card className="glass-card border-0 p-6">
            <div className="flex items-center gap-2 mb-6">
              <Trophy className="w-8 h-8 text-yellow-500" />
              <h2 className="text-2xl font-bold text-gray-900">Top Players</h2>
            </div>

            <div className="space-y-4">
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
          </Card>

          {/* Recent Games */}
          <Card className="glass-card border-0 p-6">
            <div className="flex items-center gap-2 mb-6">
              <Clock className="w-8 h-8 text-blue-500" />
              <h2 className="text-2xl font-bold text-gray-900">Recent Games</h2>
            </div>

            <div className="space-y-3">
              {recentGames.map((game) => (
                <div
                  key={game.id}
                  className={`flex items-center justify-between p-4 rounded-lg transition-all ${
                    user?.id === game.user_id 
                      ? 'bg-blue-50 border-2 border-blue-300' 
                      : 'bg-gray-50/70 hover:bg-gray-100/70'
                  }`}
                >
                  <div>
                    <div className="flex items-center gap-2">
                      <span className="text-gray-900 font-semibold">
                        {game.username}
                      </span>
                      {user?.id === game.user_id && (
                        <Badge className="bg-blue-100 text-blue-700 text-xs">
                          You
                        </Badge>
                      )}
                    </div>
                    <div className="text-gray-500 text-sm">
                      {formatDate(game.played_at)}
                    </div>
                  </div>
                  <div className="text-right">
                    <div className="text-xl font-bold text-rainbow">
                      {game.score}
                    </div>
                    <div className="text-gray-500 text-xs">
                      points
                    </div>
                  </div>
                </div>
              ))}

              {recentGames.length === 0 && (
                <div className="text-center py-8 text-gray-500">
                  <Clock className="w-12 h-12 mx-auto mb-4 opacity-50" />
                  <p>No games played yet!</p>
                </div>
              )}
            </div>
          </Card>
        </div>

        {/* User Stats */}
        {user && (
          <Card className="glass-card border-0 p-6 mt-8">
            <h2 className="text-2xl font-bold text-gray-900 mb-6">Your Stats</h2>
            <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
              <div className="text-center">
                <div className="text-3xl font-bold text-rainbow mb-2">
                  {user.highest_score}
                </div>
                <div className="text-gray-600">Personal Best</div>
              </div>
              <div className="text-center">
                <div className="text-3xl font-bold text-green-600 mb-2">
                  {recentGames.filter(g => g.user_id === user.id).length}
                </div>
                <div className="text-gray-600">Games Played</div>
              </div>
              <div className="text-center">
                <div className="text-3xl font-bold text-blue-600 mb-2">
                  {leaderboard.findIndex(p => p.id === user.id) + 1 || 'N/A'}
                </div>
                <div className="text-gray-600">Current Rank</div>
              </div>
            </div>
          </Card>
        )}
      </div>
    </div>
  );
};

export default Leaderboard;
