package com.veras.mythOrFactLGBT.service;

import com.veras.mythOrFactLGBT.model.GameHistory;
import com.veras.mythOrFactLGBT.dto.GameHistoryResponseDto; // New import
import com.veras.mythOrFactLGBT.model.User;
import java.util.List;

public interface GameHistoryService {
    GameHistory recordGame(User user, int score); // recordGame can still return the entity if no immediate DTO transformation is needed there
    List<GameHistoryResponseDto> getGameHistoryForUser(User user); // Changed return type
    List<GameHistoryResponseDto> getLeaderboardByScoreForUser(Long userId);
}
