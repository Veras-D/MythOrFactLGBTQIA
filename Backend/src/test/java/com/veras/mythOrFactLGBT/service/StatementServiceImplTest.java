package com.veras.mythOrFactLGBT.service;

import com.veras.mythOrFactLGBT.model.Statement;
import com.veras.mythOrFactLGBT.repository.StatementRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StatementServiceImplTest {

    @Mock
    private StatementRepository statementRepository;

    @InjectMocks
    private StatementServiceImpl statementService;

    private Statement statement1;
    private Statement statement2;

    @BeforeEach
    void setUp() {
        statement1 = new Statement();
        statement1.setId(1L);
        statement1.setStatement("Statement 1");
        statement1.setFact(true);
        statement1.setExplanation("Explanation 1");
        statement1.setDifficulty(1);
        statement1.setCategory("Category A");

        statement2 = new Statement();
        statement2.setId(2L);
        statement2.setStatement("Statement 2");
        statement2.setFact(false);
        statement2.setExplanation("Explanation 2");
        statement2.setDifficulty(2);
        statement2.setCategory("Category B");
    }

    @Test
    void saveStatement_success() {
        when(statementRepository.save(any(Statement.class))).thenReturn(statement1);
        Statement savedStatement = statementService.saveStatement(statement1);
        assertNotNull(savedStatement);
        assertEquals("Statement 1", savedStatement.getStatement());
        verify(statementRepository).save(statement1);
    }

    @Test
    void getStatementById_found() {
        when(statementRepository.findById(1L)).thenReturn(Optional.of(statement1));
        Optional<Statement> foundStatement = statementService.getStatementById(1L);
        assertTrue(foundStatement.isPresent());
        assertEquals("Statement 1", foundStatement.get().getStatement());
    }

    @Test
    void getStatementById_notFound() {
        when(statementRepository.findById(1L)).thenReturn(Optional.empty());
        Optional<Statement> foundStatement = statementService.getStatementById(1L);
        assertFalse(foundStatement.isPresent());
    }

    @Test
    void getAllStatements_success() {
        when(statementRepository.findAll()).thenReturn(List.of(statement1, statement2));
        List<Statement> statements = statementService.getAllStatements();
        assertEquals(2, statements.size());
    }

    @Test
    void getStatementsByCategory_success() {
        when(statementRepository.findByCategory("Category A")).thenReturn(List.of(statement1));
        List<Statement> statements = statementService.getStatementsByCategory("Category A");
        assertEquals(1, statements.size());
        assertEquals("Category A", statements.get(0).getCategory());
    }

    @Test
    void deleteStatement_success() {
        doNothing().when(statementRepository).deleteById(1L);
        statementService.deleteStatement(1L);
        verify(statementRepository, times(1)).deleteById(1L);
    }
}
