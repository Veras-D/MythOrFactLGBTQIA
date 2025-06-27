import React, { useState } from 'react';
import { AuthProvider } from '../contexts/AuthContext';
import Layout from '../components/Layout';
import Game from '../components/Game';
import Leaderboard from '../components/Leaderboard';

const Index = () => {
  const [currentPage, setCurrentPage] = useState<'game' | 'leaderboard'>('game');

  return (
    <AuthProvider>
      <Layout currentPage={currentPage} onPageChange={setCurrentPage}>
        {currentPage === 'game' ? <Game /> : <Leaderboard />}
      </Layout>
    </AuthProvider>
  );
};

export default Index;
