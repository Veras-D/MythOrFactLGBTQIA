package com.veras.mythOrFactLGBT.controller;

import com.veras.mythOrFactLGBT.dto.UserResponse;
import com.veras.mythOrFactLGBT.model.User;
import com.veras.mythOrFactLGBT.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;


@RestController
@RequestMapping("/api/users")
@Tag(name = "Users & Leaderboard", description = "User specific operations and global leaderboard")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
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

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete a user by ID", description = "Deletes a user from the system. Requires ADMIN role.")
    @ApiResponse(responseCode = "204", description = "User successfully deleted")
    @ApiResponse(responseCode = "404", description = "User not found")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (userService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/me")
    @Operation(summary = "Delete the authenticated user's account", description = "Deletes the account of the currently authenticated user.")
    @ApiResponse(responseCode = "204", description = "User account successfully deleted")
    @ApiResponse(responseCode = "401", description = "Unauthorized: User not authenticated")
    @ApiResponse(responseCode = "404", description = "User not found")
    public ResponseEntity<Void> deleteMyAccount(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(401).build();
        }
        User authenticatedUser = userService.findByUsername(userDetails.getUsername())
            .orElseThrow(() -> new RuntimeException("Authenticated user not found"));

        userService.deleteUser(authenticatedUser.getId());
        return ResponseEntity.noContent().build();
    }
}
