package com.veras.mythOrFactLGBT.service;

import com.veras.mythOrFactLGBT.model.GameHistory;
import com.veras.mythOrFactLGBT.model.User;
import com.veras.mythOrFactLGBT.repository.GameHistoryRepository;
import com.veras.mythOrFactLGBT.repository.UserRepository;
import com.veras.mythOrFactLGBT.dto.GameHistoryResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.stream.Collectors;


import java.util.List;

@Service
public class GameHistoryServiceImpl implements GameHistoryService {

    private final GameHistoryRepository gameHistoryRepository;
    private final UserRepository userRepository;

    @Autowired
    public GameHistoryServiceImpl(GameHistoryRepository gameHistoryRepository, UserRepository userRepository) {
        this.gameHistoryRepository = gameHistoryRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public GameHistory recordGame(User user, int score) {
        GameHistory gameHistory = new GameHistory();
        gameHistory.setUser(user);
        gameHistory.setScore(score);

        if (score > user.getHighestScore()) {
            user.setHighestScore(score);
            userRepository.save(user);
        }
        return gameHistoryRepository.save(gameHistory);
    }

    @Override
    @Transactional(readOnly = true)
    public List<GameHistoryResponseDto> getGameHistoryForUser(User user) {
        List<GameHistory> histories = gameHistoryRepository.findByUserOrderByPlayedAtDesc(user);
        return histories.stream()
                        .map(GameHistoryResponseDto::fromGameHistory)
                        .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<GameHistoryResponseDto> getLeaderboardByScoreForUser(Long userId) {
        

        List<GameHistory> histories = gameHistoryRepository.findByUserIdOrderByScoreDesc(userId);
        return histories.stream()
                        .map(GameHistoryResponseDto::fromGameHistory)
                        .collect(Collectors.toList());
    }
}
