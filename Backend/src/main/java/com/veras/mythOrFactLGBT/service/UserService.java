package com.veras.mythOrFactLGBT.service;

import com.veras.mythOrFactLGBT.model.User;
import java.util.Optional;
import java.util.List;
import com.veras.mythOrFactLGBT.dto.UserResponse;

public interface UserService {
    User registerUser(User user);
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Optional<User> findById(Long id);
    List<UserResponse> getGlobalLeaderboard();
    void deleteUser(Long id);
}
