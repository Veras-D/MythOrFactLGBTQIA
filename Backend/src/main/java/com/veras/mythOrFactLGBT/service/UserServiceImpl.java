package com.veras.mythOrFactLGBT.service;

import com.veras.mythOrFactLGBT.model.User;
import com.veras.mythOrFactLGBT.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.veras.mythOrFactLGBT.dto.UserResponse;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, EmailService emailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    @Transactional
    @Override
    public User registerUser(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }

        Optional<User> existingUserByEmail = userRepository.findByEmail(user.getEmail());
        if (existingUserByEmail.isPresent()) {
            User existingUser = existingUserByEmail.get();
            if (existingUser.isEmailVerified()) {
                throw new IllegalArgumentException("Email already registered. Please log in.");
            } else {
                throw new IllegalArgumentException("This email is already registered but not confirmed. Please check your email for the confirmation link.");
            }
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("USER");
        user.setConfirmationToken(UUID.randomUUID().toString());
        user.setTokenCreationDate(Timestamp.valueOf(LocalDateTime.now()));
        User savedUser = userRepository.save(user);

        emailService.sendConfirmationEmail(savedUser.getEmail(), savedUser.getConfirmationToken());

        return savedUser;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponse> getGlobalLeaderboard() {
        List<User> topUsers = userRepository.findTop10ByOrderByHighestScoreDesc();
        return topUsers.stream()
                       .map(UserResponse::fromUser)
                       .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Transactional
    @Override
    public boolean confirmUser(String token) {
        Optional<User> userOptional = userRepository.findByConfirmationToken(token);
        if (userOptional.isEmpty()) {
            return false;
        }

        User user = userOptional.get();

        Timestamp tokenCreationDate = user.getTokenCreationDate();
        LocalDateTime now = LocalDateTime.now();
        if (tokenCreationDate.toLocalDateTime().plusHours(24).isBefore(now)) {
            return false;
        }

        user.setEmailVerified(true);
        user.setConfirmationToken(null);
        user.setTokenCreationDate(null);
        userRepository.save(user);

        return true;
    }

    @Transactional
    @Override
    public String generatePasswordResetToken(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found with email: " + email));

        String token = UUID.randomUUID().toString();
        user.setResetPasswordToken(token);
        user.setResetPasswordTokenExpiry(Timestamp.valueOf(LocalDateTime.now().plusHours(1)));
        userRepository.save(user);
        return token;
    }

    @Transactional
    @Override
    public boolean resetPassword(String token, String newPassword) {
        User user = userRepository.findByResetPasswordToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Invalid or expired password reset token."));

        if (user.getResetPasswordTokenExpiry().before(Timestamp.valueOf(LocalDateTime.now()))) {
            throw new IllegalArgumentException("Password reset token has expired.");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        user.setResetPasswordToken(null);
        user.setResetPasswordTokenExpiry(null);
        userRepository.save(user);
        return true;
    }
}
