package com.veras.mythOrFactLGBT.repository;

import com.veras.mythOrFactLGBT.model.GameHistory;
import com.veras.mythOrFactLGBT.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameHistoryRepository extends JpaRepository<GameHistory, Long> {
    List<GameHistory> findByUserOrderByPlayedAtDesc(User user);
    List<GameHistory> findByUserIdOrderByScoreDesc(Long userId); // Example of finding by user ID
}
