package com.veras.mythOrFactLGBT.service;

import com.veras.mythOrFactLGBT.dto.GameHistoryResponseDto;
import com.veras.mythOrFactLGBT.model.GameHistory;
import com.veras.mythOrFactLGBT.model.User;
import com.veras.mythOrFactLGBT.repository.GameHistoryRepository;
import com.veras.mythOrFactLGBT.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GameHistoryServiceImplTest {

    @Mock
    private GameHistoryRepository gameHistoryRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private GameHistoryServiceImpl gameHistoryService;

    private User user;
    private GameHistory gameHistory;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setHighestScore(100); // Initial highest score

        gameHistory = new GameHistory();
        gameHistory.setId(1L);
        gameHistory.setUser(user);
        gameHistory.setScore(150);
        gameHistory.setPlayedAt(Timestamp.from(Instant.now()));
    }

    @Test
    void recordGame_newHighScore() {
        when(userRepository.save(any(User.class))).thenReturn(user); // Mock user save
        when(gameHistoryRepository.save(any(GameHistory.class))).thenReturn(gameHistory);

        GameHistory recordedGame = gameHistoryService.recordGame(user, 150);

        assertNotNull(recordedGame);
        assertEquals(150, recordedGame.getScore());
        assertEquals(150, user.getHighestScore()); // Verify highest score updated on user object
        verify(userRepository).save(user); // Verify user was saved due to new high score
        verify(gameHistoryRepository).save(any(GameHistory.class));
    }

    @Test
    void recordGame_scoreNotHigher() {
        // No need to mock userRepository.save() if score isn't higher, as it shouldn't be called for user.
        when(gameHistoryRepository.save(any(GameHistory.class))).thenAnswer(invocation -> invocation.getArgument(0));

        GameHistory recordedGame = gameHistoryService.recordGame(user, 50); // Score is lower than 100

        assertNotNull(recordedGame);
        assertEquals(50, recordedGame.getScore());
        assertEquals(100, user.getHighestScore()); // Highest score should remain 100
        verify(userRepository, never()).save(user); // User should not be saved
        verify(gameHistoryRepository).save(any(GameHistory.class));
    }

    @Test
    void recordGame_scoreEqualsHighScore() {
        when(gameHistoryRepository.save(any(GameHistory.class))).thenAnswer(invocation -> invocation.getArgument(0));

        GameHistory recordedGame = gameHistoryService.recordGame(user, 100); // Score is equal to current high score

        assertNotNull(recordedGame);
        assertEquals(100, recordedGame.getScore());
        assertEquals(100, user.getHighestScore());
        verify(userRepository, never()).save(user);
        verify(gameHistoryRepository).save(any(GameHistory.class));
    }


    @Test
    void getGameHistoryForUser_success() {
        when(gameHistoryRepository.findByUserOrderByPlayedAtDesc(user)).thenReturn(List.of(gameHistory));
        List<GameHistoryResponseDto> histories = gameHistoryService.getGameHistoryForUser(user);
        assertEquals(1, histories.size());
        assertEquals(150, histories.get(0).getScore());
    }

    @Test
    void getLeaderboardByScoreForUser_success() {
        when(gameHistoryRepository.findByUserIdOrderByScoreDesc(1L)).thenReturn(List.of(gameHistory));
        List<GameHistoryResponseDto> leaderboard = gameHistoryService.getLeaderboardByScoreForUser(1L);
        assertEquals(1, leaderboard.size());
        assertEquals(150, leaderboard.get(0).getScore());
    }
}
