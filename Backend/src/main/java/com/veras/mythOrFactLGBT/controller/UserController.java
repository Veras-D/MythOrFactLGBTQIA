package com.veras.mythOrFactLGBT.controller;

import com.veras.mythOrFactLGBT.dto.UserResponse;
import com.veras.mythOrFactLGBT.model.User;
import com.veras.mythOrFactLGBT.service.UserService;
// Import for @PreAuthorize if using method-level security
// import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation; // Added
import io.swagger.v3.oas.annotations.media.ArraySchema; // Added
import io.swagger.v3.oas.annotations.media.Content; // Added
import io.swagger.v3.oas.annotations.media.Schema; // Added
import io.swagger.v3.oas.annotations.responses.ApiResponse; // Added
import io.swagger.v3.oas.annotations.tags.Tag; // Added
import java.util.List; // Added


@RestController
@RequestMapping("/api")
@Tag(name = "Users & Leaderboard", description = "User specific operations and global leaderboard")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    // @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(401).body("User not authenticated");
        }
        User user = userService.findByUsername(userDetails.getUsername())
            .orElseThrow(() -> new RuntimeException("User not found: " + userDetails.getUsername()));
        return ResponseEntity.ok(UserResponse.fromUser(user));
    }

    @GetMapping("/{id}")
    
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        User user = userService.findById(id)
            .orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(UserResponse.fromUser(user));
    }

    @GetMapping("/leaderboard")
    @Operation(summary = "Get global leaderboard", description = "Retrieves the top 10 users by highest score.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved leaderboard",
                 content = @Content(mediaType = "application/json",
                                   array = @ArraySchema(schema = @Schema(implementation = UserResponse.class))))
    public ResponseEntity<List<UserResponse>> getGlobalLeaderboard() {
        List<UserResponse> leaderboard = userService.getGlobalLeaderboard();
        return ResponseEntity.ok(leaderboard);
    }
}
