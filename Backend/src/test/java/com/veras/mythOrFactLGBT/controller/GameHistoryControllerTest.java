package com.veras.mythOrFactLGBT.controller;

import com.veras.mythOrFactLGBT.dto.GameHistoryResponseDto;
import com.veras.mythOrFactLGBT.model.GameHistory;
import com.veras.mythOrFactLGBT.model.User;
import com.veras.mythOrFactLGBT.service.GameHistoryService;
import com.veras.mythOrFactLGBT.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.context.ActiveProfiles;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

@WebMvcTest(GameHistoryController.class)
@AutoConfigureMockMvc
// @ActiveProfiles("test")
class GameHistoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private GameHistoryService gameHistoryService;

    @MockBean
    private UserService userService;

    private User testUser;
    private GameHistory gameHistory1;
    private GameHistoryResponseDto gameHistoryResponseDto;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("testuser");
        testUser.setEmail("test@example.com");
        testUser.setCreatedAt(Timestamp.from(Instant.now()));
        testUser.setHighestScore(100);

        gameHistory1 = new GameHistory();
        gameHistory1.setId(1L);
        gameHistory1.setUser(testUser);
        gameHistory1.setScore(150);
        gameHistory1.setPlayedAt(Timestamp.from(Instant.now()));

        gameHistoryResponseDto = new GameHistoryResponseDto(
                gameHistory1.getId(),
                testUser.getId(),
                testUser.getUsername(),
                gameHistory1.getScore(),
                gameHistory1.getPlayedAt()
        );
    }

    @Test
    @WithMockUser(username = "testuser")
    void recordGameHistory_success() throws Exception {
        when(userService.findByUsername("testuser")).thenReturn(Optional.of(testUser));
        when(gameHistoryService.recordGame(eq(testUser), eq(150))).thenReturn(gameHistory1);

        Map<String, Integer> payload = new HashMap<>();
        payload.put("score", 150);

        mockMvc.perform(post("/api/gamehistory")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(payload)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.score").value(150))
                .andExpect(jsonPath("$.userId").value(testUser.getId()));
    }

    @Test
    @WithMockUser(username = "testuser")
    void recordGameHistory_missingScore() throws Exception {
        Map<String, Integer> payload = new HashMap<>();

        mockMvc.perform(post("/api/gamehistory")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(payload)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Score is required"));
    }

    @Test
    void recordGameHistory_unauthenticated() throws Exception {
         Map<String, Integer> payload = new HashMap<>();
         payload.put("score", 150);
        mockMvc.perform(post("/api/gamehistory")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(payload)))
                .andExpect(status().isForbidden());
    }


    @Test
    @WithMockUser(username = "testuser")
    void getCurrentUserGameHistory_success() throws Exception {
        when(userService.findByUsername("testuser")).thenReturn(Optional.of(testUser));
        when(gameHistoryService.getGameHistoryForUser(testUser)).thenReturn(List.of(gameHistoryResponseDto));

        mockMvc.perform(get("/api/gamehistory/user/me"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].score").value(150));
    }

    @Test
    @WithMockUser(username = "otheruser")
    void getUserGameHistory_success() throws Exception {
        User targetUser = new User();
        targetUser.setId(2L);
        targetUser.setUsername("targetUser");

        when(userService.findById(2L)).thenReturn(Optional.of(targetUser));
        when(gameHistoryService.getGameHistoryForUser(targetUser)).thenReturn(List.of(gameHistoryResponseDto));

        mockMvc.perform(get("/api/gamehistory/user/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    @WithMockUser(username = "testuser")
    void getUserGameHistory_userNotFound() throws Exception {
        when(userService.findById(3L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/gamehistory/user/3"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "testuser")
    void getLeaderboardForUser_success() throws Exception {
        User targetUser = new User();
        targetUser.setId(1L);
        when(userService.findById(1L)).thenReturn(Optional.of(targetUser));
        when(gameHistoryService.getLeaderboardByScoreForUser(1L)).thenReturn(List.of(gameHistoryResponseDto));

        mockMvc.perform(get("/api/gamehistory/leaderboard/user/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].score").value(150));
    }

    @Test
    @WithMockUser(username = "testuser")
    void getLeaderboardForUser_userNotFound() throws Exception {
        when(userService.findById(1L)).thenReturn(Optional.empty());
        mockMvc.perform(get("/api/gamehistory/leaderboard/user/1"))
            .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "testuser")
    void getLeaderboardForUser_noHistory() throws Exception {
        User targetUser = new User();
        targetUser.setId(1L);
        when(userService.findById(1L)).thenReturn(Optional.of(targetUser));
        when(gameHistoryService.getLeaderboardByScoreForUser(1L)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/gamehistory/leaderboard/user/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(0)));
    }

}
