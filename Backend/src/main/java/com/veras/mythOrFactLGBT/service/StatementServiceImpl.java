package com.veras.mythOrFactLGBT.service;

import com.veras.mythOrFactLGBT.model.Statement;
import com.veras.mythOrFactLGBT.repository.StatementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;

@Service
public class StatementServiceImpl implements StatementService {

    private final StatementRepository statementRepository;

    @Autowired
    public StatementServiceImpl(StatementRepository statementRepository) {
        this.statementRepository = statementRepository;
    }

    @Transactional
    @Override
    public Statement saveStatement(Statement statement) {
        return statementRepository.save(statement);
    }

    @Override
    public Optional<Statement> getStatementById(Long id) {
        return statementRepository.findById(id);
    }

    @Override
    public List<Statement> getAllStatements() {
        return statementRepository.findAll();
    }

    @Override
    public List<Statement> getStatementsByCategory(String category) {
        return statementRepository.findByCategory(category);
    }

    @Override
    public List<Statement> getStatementsByDifficulty(Integer difficulty) {
        return statementRepository.findByDifficulty(difficulty);
    }

    @Override
    public List<Statement> getStatementsByCategoryAndDifficulty(String category, Integer difficulty) {
        return statementRepository.findByCategoryAndDifficulty(category, difficulty);
    }

    @Transactional
    @Override
    public void deleteStatement(Long id) {
        statementRepository.deleteById(id);
    }
}
