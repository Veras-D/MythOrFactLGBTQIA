package com.veras.mythOrFactLGBT.controller;

import com.veras.mythOrFactLGBT.dto.AuthResponse;
import com.veras.mythOrFactLGBT.dto.LoginRequest;
import com.veras.mythOrFactLGBT.dto.RegisterRequest;
import com.veras.mythOrFactLGBT.model.User;
import com.veras.mythOrFactLGBT.security.JwtUtil;
import com.veras.mythOrFactLGBT.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;
import com.veras.mythOrFactLGBT.dto.ForgotPasswordRequest;
import com.veras.mythOrFactLGBT.dto.ResetPasswordRequest;
import com.veras.mythOrFactLGBT.service.EmailService;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "Endpoints for user registration and login")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final EmailService emailService;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, UserService userService, JwtUtil jwtUtil, EmailService emailService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.emailService = emailService;
    }

    @Operation(summary = "Register a new user")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "User registered successfully",
                     content = @Content(mediaType = "text/plain")),
        @ApiResponse(responseCode = "400", description = "Invalid input (e.g., username/email already exists)",
                     content = @Content(mediaType = "text/plain"))
    })
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        try {
            User newUser = new User();
            newUser.setUsername(registerRequest.getUsername());
            newUser.setEmail(registerRequest.getEmail());
            newUser.setPassword(registerRequest.getPassword());
            User registeredUser = userService.registerUser(newUser);
            return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully. ID: " + registeredUser.getId());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Authenticate a user and return a JWT token")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Authentication successful",
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthResponse.class))),
        @ApiResponse(responseCode = "401", description = "Invalid credentials",
                     content = @Content(mediaType = "application/json"))
    })
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtil.generateToken(authentication);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userService.findByUsername(userDetails.getUsername()).orElseThrow(() -> new RuntimeException("User not found after authentication"));

        if (!user.isEmailVerified()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Please confirm your email before logging in.");
        }

        return ResponseEntity.ok(new AuthResponse(jwt, user.getId(), user.getUsername()));
    }

    @Operation(summary = "Confirm user's email address")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Email confirmed successfully",
                     content = @Content(mediaType = "text/plain")),
        @ApiResponse(responseCode = "400", description = "Invalid or expired token",
                     content = @Content(mediaType = "text/plain"))
    })
    @GetMapping("/confirm")
    public ResponseEntity<String> confirmEmail(@RequestParam("token") String token) {
        boolean isConfirmed = userService.confirmUser(token);

        if (isConfirmed) {
            String successHtml = """
    <!DOCTYPE html>
    <html lang="en">
      <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Email Confirmed Successfully</title>
        <style>
          body {
            background-color: #fff8f9;
            font-family: "Segoe UI", Tahoma, Geneva, Verdana, sans-serif;
            color: #333;
            text-align: center;
            padding: 2rem;
            margin: 0;
          }
          .card {
            background-color: white;
            border-radius: 12px;
            box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
            padding: 2rem;
            max-width: 500px;
            margin: 0 auto;
          }
          .success-icon {
            font-size: 3rem;
            margin-bottom: 1rem;
          }
          .highlight {
            font-weight: bold;
            color: #00c37a;
          }
          a.button {
            display: inline-block;
            background-color: #00c37a;
            color: white;
            padding: 12px 24px;
            border-radius: 8px;
            text-decoration: none;
            font-weight: bold;
            margin-top: 1.5rem;
            transition: background-color 0.3s;
          }
          a.button:hover {
            background-color: #00a265;
          }
          .footer {
            font-size: 0.9rem;
            margin-top: 2rem;
            color: #999;
          }
        </style>
      </head>
      <body>
        <div class="card">
          <div class="success-icon">✅</div>
          <h2>🌈 Email Confirmed Successfully!</h2>
          <p>
            Welcome to our colorful world of
            <span class="highlight">truths and fun facts</span> 🌟
          </p>
          <p>Your email has been verified and your account is now active.</p>
          <a href="https://myth-or-fact-lgbtqia.vercel.app/" class="button">🚀 Start Exploring</a>
        </div>
        <p class="footer">
          Thank you for joining our community! 🏳️‍🌈
        </p>
      </body>
    </html>
    """;
            return ResponseEntity.ok().contentType(MediaType.TEXT_HTML).body(successHtml);
        } else {
            String errorHtml = """
    <!DOCTYPE html>
    <html lang="en">
      <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Email Confirmation Failed</title>
        <style>
          body {
            background-color: #fff8f9;
            font-family: "Segoe UI", Tahoma, Geneva, Verdana, sans-serif;
            color: #333;
            text-align: center;
            padding: 2rem;
            margin: 0;
          }
          .card {
            background-color: white;
            border-radius: 12px;
            box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
            padding: 2rem;
            max-width: 500px;
            margin: 0 auto;
          }
          .error-icon {
            font-size: 3rem;
            margin-bottom: 1rem;
          }
          .error-text {
            font-weight: bold;
            color: #e74c3c;
          }
          a.button {
            display: inline-block;
            background-color: #00c37a;
            color: white;
            padding: 12px 24px;
            border-radius: 8px;
            text-decoration: none;
            font-weight: bold;
            margin-top: 1.5rem;
            transition: background-color 0.3s;
          }
          a.button:hover {
            background-color: #00a265;
          }
          .footer {
            font-size: 0.9rem;
            margin-top: 2rem;
            color: #999;
          }
        </style>
      </head>
      <body>
        <div class="card">
          <div class="error-icon">❌</div>
          <h2>🌈 Email Confirmation Failed</h2>
          <p class="error-text">
            Invalid or expired confirmation token.
          </p>
          <p>Please try signing up again or contact support if the issue persists.</p>
          <a href="https://myth-or-fact-lgbtqia.vercel.app/" class="button">🏠 Go to Home</a>
        </div>
        <p class="footer">
          Need help? Contact our support team! 💪
        </p>
      </body>
    </html>
    """;
            return ResponseEntity.badRequest().contentType(MediaType.TEXT_HTML).body(errorHtml);
        }
    }

    @Operation(summary = "Request a password reset link")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "If an account exists, a password reset link has been sent to your email.",
                     content = @Content(mediaType = "text/plain")),
        @ApiResponse(responseCode = "400", description = "Invalid email format",
                     content = @Content(mediaType = "text/plain"))
    })
    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@Valid @RequestBody ForgotPasswordRequest request) {
        try {
            String resetToken = userService.generatePasswordResetToken(request.getEmail());
            emailService.sendPasswordResetEmail(request.getEmail(), resetToken);
            return ResponseEntity.ok("If an account with that email exists, a password reset link has been sent.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.ok("If an account with that email exists, a password reset link has been sent.");
        } catch (Exception e) {
            return ResponseEntity.ok("If an account with that email exists, a password reset link has been sent.");
        }
    }

    @Operation(summary = "Reset user password using a token")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Password has been reset successfully.",
                     content = @Content(mediaType = "text/plain")),
        @ApiResponse(responseCode = "400", description = "Invalid or expired token, or invalid password",
                     content = @Content(mediaType = "text/plain"))
    })
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
        try {
            userService.resetPassword(request.getToken(), request.getNewPassword());
            return ResponseEntity.ok("Password has been reset successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
