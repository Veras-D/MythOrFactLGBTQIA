package com.veras.mythOrFactLGBT.repository;

import com.veras.mythOrFactLGBT.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    List<User> findTop10ByOrderByHighestScoreDesc();
    Optional<User> findByConfirmationToken(String token);
}
