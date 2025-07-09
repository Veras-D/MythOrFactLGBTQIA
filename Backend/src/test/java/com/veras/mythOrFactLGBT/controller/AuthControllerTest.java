package com.veras.mythOrFactLGBT.controller;

import com.veras.mythOrFactLGBT.dto.LoginRequest;
import com.veras.mythOrFactLGBT.dto.RegisterRequest;
import com.veras.mythOrFactLGBT.model.User;
import com.veras.mythOrFactLGBT.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;
import com.veras.mythOrFactLGBT.security.JwtUtil;
import com.veras.mythOrFactLGBT.service.EmailService;
import com.veras.mythOrFactLGBT.dto.ForgotPasswordRequest;
import com.veras.mythOrFactLGBT.dto.ResetPasswordRequest;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.never;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
// @ActiveProfiles("test")
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private JwtUtil jwtUtil;

    @MockBean
    private EmailService emailService;

    @Test
    void registerUser_success() throws Exception {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("newuser");
        registerRequest.setEmail("new@example.com");
        registerRequest.setPassword("password123");

        User registeredUser = new User();
        registeredUser.setId(1L);
        registeredUser.setUsername("newuser");
        registeredUser.setEmail("new@example.com");
        registeredUser.setConfirmationToken("some-token");
        registeredUser.setEmail("new@example.com");
        registeredUser.setConfirmationToken("some-token");

        when(userService.registerUser(any(User.class))).thenReturn(registeredUser);

        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isCreated())
                .andExpect(content().string("User registered successfully. ID: 1"));
    }

    @Test
    void registerUser_usernameExists() throws Exception {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("existinguser");
        registerRequest.setEmail("new@example.com");
        registerRequest.setPassword("password123");

        when(userService.registerUser(any(User.class))).thenThrow(new IllegalArgumentException("Username already exists"));

        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Username already exists"));
    }

    @Test
    void authenticateUser_success() throws Exception {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("testuser");
        loginRequest.setPassword("password");

        Authentication authentication = mock(Authentication.class);
        org.springframework.security.core.userdetails.User userDetails =
            new org.springframework.security.core.userdetails.User("testuser", "password", List.of());


        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
            .thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);


        User appUser = new User();
        appUser.setId(1L);
        appUser.setUsername("testuser");
        appUser.setEmailVerified(true);
        when(userService.findByUsername("testuser")).thenReturn(Optional.of(appUser));

        when(jwtUtil.generateToken(authentication)).thenReturn("mocked.jwt.token");


        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("mocked.jwt.token"))
                .andExpect(jsonPath("$.userId").value(1L))
                .andExpect(jsonPath("$.username").value("testuser"));
    }

    @Test
    void forgotPassword_success() throws Exception {
        String email = "test@example.com";
        ForgotPasswordRequest forgotPasswordRequest = new ForgotPasswordRequest();
        forgotPasswordRequest.setEmail(email);

        when(userService.generatePasswordResetToken(email)).thenReturn("reset-token");

        mockMvc.perform(post("/api/auth/forgot-password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(forgotPasswordRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string("If an account with that email exists, a password reset link has been sent."));

        verify(emailService).sendPasswordResetEmail(email, "reset-token");
    }

    @Test
    void forgotPassword_userNotFound() throws Exception {
        String email = "nonexistent@example.com";
        ForgotPasswordRequest forgotPasswordRequest = new ForgotPasswordRequest();
        forgotPasswordRequest.setEmail(email);

        when(userService.generatePasswordResetToken(email)).thenThrow(new IllegalArgumentException("User not found"));

        mockMvc.perform(post("/api/auth/forgot-password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(forgotPasswordRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string("If an account with that email exists, a password reset link has been sent."));

        verify(emailService, never()).sendPasswordResetEmail(anyString(), anyString());
    }

    @Test
    void resetPassword_success() throws Exception {
        String token = "valid-token";
        String newPassword = "newPassword123";
        ResetPasswordRequest resetPasswordRequest = new ResetPasswordRequest();
        resetPasswordRequest.setToken(token);
        resetPasswordRequest.setNewPassword(newPassword);

        when(userService.resetPassword(token, newPassword)).thenReturn(true);

        mockMvc.perform(post("/api/auth/reset-password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(resetPasswordRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string("Password has been reset successfully."));
    }

    @Test
    void resetPassword_invalidToken() throws Exception {
        String token = "invalid-token";
        String newPassword = "newPassword123";
        ResetPasswordRequest resetPasswordRequest = new ResetPasswordRequest();
        resetPasswordRequest.setToken(token);
        resetPasswordRequest.setNewPassword(newPassword);

        when(userService.resetPassword(token, newPassword)).thenThrow(new IllegalArgumentException("Invalid or expired password reset token."));

        mockMvc.perform(post("/api/auth/reset-password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(resetPasswordRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid or expired password reset token."));
    }
}
