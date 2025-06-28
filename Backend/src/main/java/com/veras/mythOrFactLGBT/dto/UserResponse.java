package com.veras.mythOrFactLGBT.dto;

import com.veras.mythOrFactLGBT.model.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "Response object containing user details (excluding password).")
public class UserResponse {

    @Schema(description = "Unique identifier for the user", example = "1")
    private Long id;

    @Schema(description = "Username of the user", example = "testuser")
    private String username;

    @Schema(description = "Email address of the user", example = "user@example.com")
    private String email;

    @Schema(description = "Timestamp of user account creation")
    private Timestamp createdAt;

    @Schema(description = "User's highest achieved score in the game", example = "5000")
    private Integer highestScore;

    public UserResponse(Long id, String username, String email, Timestamp createdAt, Integer highestScore) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.createdAt = createdAt;
        this.highestScore = highestScore;
    }

    public static UserResponse fromUser(User user) {
        if (user == null) {
            return null;
        }
        return new UserResponse(
            user.getId(),
            user.getUsername(),
            user.getEmail(),
            user.getCreatedAt(),
            user.getHighestScore()
        );
    }
}
