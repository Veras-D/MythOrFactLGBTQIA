package com.veras.mythOrFactLGBT.repository;

import com.veras.mythOrFactLGBT.model.Statement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatementRepository extends JpaRepository<Statement, Long> {
    List<Statement> findByCategory(String category);
    List<Statement> findByDifficulty(Integer difficulty);
    List<Statement> findByCategoryAndDifficulty(String category, Integer difficulty);
}
