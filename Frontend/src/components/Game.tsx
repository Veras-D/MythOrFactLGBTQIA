import React, { useState, useEffect, useCallback } from 'react';
import { useAuth } from '../contexts/AuthContext';
import { Statement, fetchStatements, getRandomStatement, getDifficultyColor, getDifficultyLabel, getCategoryColor } from '../data/statements';
import { Button } from './ui/button';
import { Card } from './ui/card';
import { Badge } from './ui/badge';
import { toast } from 'sonner';
import { Check, X, Trophy, Star } from 'lucide-react';

const Game: React.FC = () => {
  const { user, updateHighScore, saveGameHistory } = useAuth();
  const [currentStatement, setCurrentStatement] = useState<Statement | null>(null);
  const [score, setScore] = useState(0);
  const [gameOver, setGameOver] = useState(false);
  const [showExplanation, setShowExplanation] = useState(false);
  const [usedStatements, setUsedStatements] = useState<number[]>([]);
  const [streak, setStreak] = useState(0);
  const [selectedAnswer, setSelectedAnswer] = useState<boolean | null>(null);
  const [isAnswering, setIsAnswering] = useState(false);

  const endGame = useCallback(() => {
    setGameOver(true);
    if (user) {
      updateHighScore(score);
      saveGameHistory(score);
    }
  }, [user, score, updateHighScore, saveGameHistory]);

  const loadNextStatement = useCallback(async (used: number[]) => {
    const statement = await getRandomStatement(used);
    if (statement) {
      setCurrentStatement(statement);
      setShowExplanation(false);
      setSelectedAnswer(null);
      setIsAnswering(false);
    } else {
      endGame();
    }
  }, [endGame]);

  const startNewGame = useCallback(() => {
    setScore(0);
    setGameOver(false);
    setShowExplanation(false);
    setUsedStatements([]);
    setStreak(0);
    setSelectedAnswer(null);
    setIsAnswering(false);
    loadNextStatement([]);
  }, [loadNextStatement]);

  useEffect(() => {
    startNewGame();
  }, [startNewGame]);

  const handleAnswer = useCallback((answer: boolean) => {
    if (isAnswering || !currentStatement) return;

    setIsAnswering(true);
    setSelectedAnswer(answer);
    setShowExplanation(true);

    const isCorrect = answer === currentStatement.fact;

    if (isCorrect) {
      const points = currentStatement.difficulty * 10;
      setScore(prev => prev + points);
      setStreak(prev => prev + 1);
      toast.success(`Correct! +${points} points`, {
        icon: <Trophy className="w-4 h-4 text-yellow-400" />
      });

      setTimeout(() => {
        const newUsed = [...usedStatements, currentStatement.id];
        setUsedStatements(newUsed);
        loadNextStatement(newUsed);
      }, 3000);
    } else {
      toast.error('Wrong answer! Game over!', {
        icon: <X className="w-4 h-4 text-red-400" />
      });
      setTimeout(() => {
        endGame();
      }, 3000);
    }
  }, [isAnswering, currentStatement, usedStatements, loadNextStatement, endGame]);

  if (gameOver) {
    return (
      <div className="min-h-screen light-bg flex items-center justify-center p-4">
        <Card className="w-full max-w-2xl glass-card border-0 p-8 text-center">
          <div className="mb-6">
            <Trophy className="w-16 h-16 text-yellow-500 mx-auto mb-4 float" />
            <h2 className="text-4xl font-bold text-gray-900 mb-4">Game Over!</h2>
            <div className="space-y-2">
              <p className="text-2xl text-gray-900">Final Score: <span className="text-rainbow font-bold">{score}</span></p>
              <p className="text-lg text-gray-700">Correct Answers: {usedStatements.length}</p>
              {user && user.highest_score === score && (
                <div className="flex items-center justify-center gap-2 text-yellow-500">
                  <Star className="w-5 h-5" />
                  <span className="font-semibold text-gray-900">New Personal Best!</span>
                  <Star className="w-5 h-5" />
                </div>
              )}
            </div>
          </div>

          <div className="space-y-4">
            <Button
              onClick={startNewGame}
              className="w-full pride-gradient text-white font-semibold py-3 btn-hover"
            >
              Play Again
            </Button>
          </div>
        </Card>
      </div>
    );
  }

  if (!currentStatement) {
    return (
      <div className="min-h-screen light-bg flex items-center justify-center">
        <div className="text-gray-900 text-xl">Loading...</div>
      </div>
    );
  }

  return (
    <div className="min-h-screen light-bg flex items-center justify-center p-4">
      <div className="w-full max-w-4xl">
        {/* Score Header */}
        <div className="mb-6 text-center">
          <div className="glass-card rounded-2xl p-4 mb-4">
            <div className="flex justify-between items-center text-gray-900">
              <div>
                <span className="text-lg font-semibold">Score: </span>
                <span className="text-2xl font-bold text-rainbow">{score}</span>
              </div>
              <div>
                <span className="text-lg font-semibold">Streak: </span>
                <span className="text-2xl font-bold text-yellow-500">{streak}</span>
              </div>
              {user && (
                <div>
                  <span className="text-lg font-semibold">Best: </span>
                  <span className="text-2xl font-bold text-green-600">{user.highest_score}</span>
                </div>
              )}
            </div>
          </div>
        </div>

        {/* Statement Card */}
        <Card className="glass-card border-0 p-8 mb-6">
          <div className="mb-4 flex items-center justify-between">
            <Badge className={`${getDifficultyColor(currentStatement.difficulty)} text-white font-semibold transition-colors cursor-default`}>
              {getDifficultyLabel(currentStatement.difficulty)}
            </Badge>
            <Badge className={`${getCategoryColor(currentStatement.category)} text-white font-semibold transition-colors cursor-default`}>
              {currentStatement.category}
            </Badge>
          </div>

          <h3 className="text-2xl font-bold text-gray-900 mb-6 leading-relaxed">
            {currentStatement.statement}
          </h3>

          {!showExplanation ? (
            <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
              <Button
                onClick={() => handleAnswer(false)}
                disabled={isAnswering}
                className="h-16 text-xl font-bold bg-red-500/80 hover:bg-red-500 text-white btn-hover border-2 border-red-400"
              >
                <X className="w-8 h-8 mr-2" />
                MYTH
              </Button>
              <Button
                onClick={() => handleAnswer(true)}
                disabled={isAnswering}
                className="h-16 text-xl font-bold bg-green-500/80 hover:bg-green-500 text-white btn-hover border-2 border-green-400"
              >
                <Check className="w-8 h-8 mr-2" />
                FACT
              </Button>
            </div>
          ) : (
            <div className="space-y-4">
              <div className={`p-4 rounded-lg border-2 ${
                selectedAnswer === currentStatement.fact
                  ? 'bg-green-100 border-green-500'
                  : 'bg-red-100 border-red-500'
              }`}>
                <div className="flex items-center gap-2 mb-2">
                  {selectedAnswer === currentStatement.fact ? (
                    <>
                      <Check className="w-6 h-6 text-green-600" />
                      <span className="text-green-700 font-bold text-lg">Correct!</span>
                    </>
                  ) : (
                    <>
                      <X className="w-6 h-6 text-red-600" />
                      <span className="text-red-700 font-bold text-lg">Incorrect!</span>
                    </>
                  )}
                </div>
                <p className="text-gray-900 font-semibold">
                  This statement is a {currentStatement.fact ? 'FACT' : 'MYTH'}
                </p>
              </div>

              <div className="p-4 bg-gray-100 rounded-lg border border-gray-300">
                <h4 className="text-gray-900 font-semibold mb-2">Explanation:</h4>
                <p className="text-gray-800 leading-relaxed">
                  {currentStatement.explanation}
                </p>
              </div>

              {selectedAnswer === currentStatement.is_fact && (
                <div className="text-center">
                  <p className="text-yellow-600 font-semibold">
                    +{currentStatement.difficulty * 10} points earned!
                  </p>
                  <p className="text-gray-600 text-sm mt-1">
                    Next question in 3 seconds...
                  </p>
                </div>
              )}
            </div>
          )}
        </Card>
      </div>
    </div>
  );
};

export default Game;
