package com.veras.mythOrFactLGBT.service;

import com.veras.mythOrFactLGBT.model.GameHistory;
import com.veras.mythOrFactLGBT.dto.GameHistoryResponseDto;
import com.veras.mythOrFactLGBT.model.User;
import java.util.List;

public interface GameHistoryService {
    GameHistory recordGame(User user, int score);
    List<GameHistoryResponseDto> getGameHistoryForUser(User user);
    List<GameHistoryResponseDto> getLeaderboardByScoreForUser(Long userId);
}
