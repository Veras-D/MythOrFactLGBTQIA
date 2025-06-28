package com.veras.mythOrFactLGBT.dto;

import com.veras.mythOrFactLGBT.model.GameHistory;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Response object for game history entries, including username.")
public class GameHistoryResponseDto {

    @Schema(description = "Unique identifier for the game history record")
    private Long id;

    @Schema(description = "Identifier of the user who played the game")
    private Long userId;

    @Schema(description = "Username of the user who played the game", example = "testuser")
    private String username;

    @Schema(description = "Score achieved in the game", example = "1500")
    private Integer score;

    @Schema(description = "Timestamp when the game was played")
    private Timestamp playedAt;

    public static GameHistoryResponseDto fromGameHistory(GameHistory gameHistory) {
        if (gameHistory == null) {
            return null;
        }
        return new GameHistoryResponseDto(
            gameHistory.getId(),
            gameHistory.getUser().getId(),
            gameHistory.getUser().getUsername(),
            gameHistory.getScore(),
            gameHistory.getPlayedAt()
        );
    }
}
