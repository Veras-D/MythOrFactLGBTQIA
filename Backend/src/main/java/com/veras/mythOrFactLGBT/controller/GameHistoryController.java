package com.veras.mythOrFactLGBT.controller;

import com.veras.mythOrFactLGBT.model.GameHistory;
import com.veras.mythOrFactLGBT.model.User;
import com.veras.mythOrFactLGBT.service.GameHistoryService;
import com.veras.mythOrFactLGBT.service.UserService;
import com.veras.mythOrFactLGBT.dto.GameHistoryResponseDto; // New import
import io.swagger.v3.oas.annotations.media.ArraySchema; // Added/Ensure
import io.swagger.v3.oas.annotations.media.Content; // Added/Ensure
import io.swagger.v3.oas.annotations.media.Schema; // Added/Ensure
import io.swagger.v3.oas.annotations.responses.ApiResponse; // Added/Ensure
import io.swagger.v3.oas.annotations.Operation; // Added/Ensure
import io.swagger.v3.oas.annotations.tags.Tag; // Added/Ensure
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
// import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/gamehistory")
public class GameHistoryController {

    private final GameHistoryService gameHistoryService;
    private final UserService userService;

    @Autowired
    public GameHistoryController(GameHistoryService gameHistoryService, UserService userService) {
        this.gameHistoryService = gameHistoryService;
        this.userService = userService;
    }

    @PostMapping
    @Operation(summary = "Record a new game session for the authenticated user")
    // Ensure existing @ApiResponses are appropriate or add new ones if this response changes
    public ResponseEntity<?> recordGameHistory(@AuthenticationPrincipal UserDetails userDetails,
                                                 @RequestBody Map<String, Integer> payload) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
        }
        Integer score = payload.get("score");
        if (score == null) {
            return ResponseEntity.badRequest().body("Score is required");
        }

        User user = userService.findByUsername(userDetails.getUsername())
            .orElseThrow(() -> new RuntimeException("User not found: " + userDetails.getUsername()));

        GameHistory gameRecord = gameHistoryService.recordGame(user, score);
        // Return the DTO version:
        return ResponseEntity.status(HttpStatus.CREATED).body(GameHistoryResponseDto.fromGameHistory(gameRecord));
    }

    @GetMapping("/user/me")
    @Operation(summary = "Get game history for the currently authenticated user")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved game history",
                 content = @Content(mediaType = "application/json",
                                   array = @ArraySchema(schema = @Schema(implementation = GameHistoryResponseDto.class))))
    public ResponseEntity<List<GameHistoryResponseDto>> getCurrentUserGameHistory(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        User user = userService.findByUsername(userDetails.getUsername())
            .orElseThrow(() -> new RuntimeException("User not found: " + userDetails.getUsername()));
        List<GameHistoryResponseDto> history = gameHistoryService.getGameHistoryForUser(user); // Already returns DTO list
        return ResponseEntity.ok(history);
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get game history for a specific user by ID")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved game history for the user",
                 content = @Content(mediaType = "application/json",
                                   array = @ArraySchema(schema = @Schema(implementation = GameHistoryResponseDto.class))))
    public ResponseEntity<List<GameHistoryResponseDto>> getUserGameHistory(@PathVariable Long userId) {
        User user = userService.findById(userId)
            .orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        List<GameHistoryResponseDto> history = gameHistoryService.getGameHistoryForUser(user);
        return ResponseEntity.ok(history);
    }

    @GetMapping("/leaderboard/user/{userId}")
    @Operation(summary = "Get score-ordered game history for a specific user (user's personal bests)")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved score leaderboard for the user",
                 content = @Content(mediaType = "application/json",
                                   array = @ArraySchema(schema = @Schema(implementation = GameHistoryResponseDto.class))))
    public ResponseEntity<List<GameHistoryResponseDto>> getLeaderboardForUser(@PathVariable Long userId) {
        List<GameHistoryResponseDto> leaderboardEntries = gameHistoryService.getLeaderboardByScoreForUser(userId);
        if (leaderboardEntries.isEmpty()) {
            if(userService.findById(userId).isEmpty()){
                return ResponseEntity.notFound().build();
            }
        }
        return ResponseEntity.ok(leaderboardEntries);
    }
}
