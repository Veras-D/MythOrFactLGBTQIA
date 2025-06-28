package com.veras.mythOrFactLGBT.controller;

import com.veras.mythOrFactLGBT.model.Statement;
import com.veras.mythOrFactLGBT.service.StatementService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/statements")
public class StatementController {

    private final StatementService statementService;

    @Autowired
    public StatementController(StatementService statementService) {
        this.statementService = statementService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Statement> createStatement(@Valid @RequestBody Statement statement) {
        Statement savedStatement = statementService.saveStatement(statement);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedStatement);
    }

    @GetMapping
    public ResponseEntity<List<Statement>> getAllStatements(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Integer difficulty) {
        List<Statement> statements;
        if (category != null && difficulty != null) {
            statements = statementService.getStatementsByCategoryAndDifficulty(category, difficulty);
        } else if (category != null) {
            statements = statementService.getStatementsByCategory(category);
        } else if (difficulty != null) {
            statements = statementService.getStatementsByDifficulty(difficulty);
        } else {
            statements = statementService.getAllStatements();
        }
        return ResponseEntity.ok(statements);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Statement> getStatementById(@PathVariable Long id) {
        Optional<Statement> statement = statementService.getStatementById(id);
        return statement.map(ResponseEntity::ok)
                       .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Statement> updateStatement(@PathVariable Long id, @Valid @RequestBody Statement statementDetails) {
        Optional<Statement> optionalStatement = statementService.getStatementById(id);
        if (optionalStatement.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Statement existingStatement = optionalStatement.get();
        existingStatement.setStatement(statementDetails.getStatement());
        existingStatement.setFact(statementDetails.isFact());
        existingStatement.setExplanation(statementDetails.getExplanation());
        existingStatement.setDifficulty(statementDetails.getDifficulty());
        existingStatement.setCategory(statementDetails.getCategory());
        Statement updatedStatement = statementService.saveStatement(existingStatement);
        return ResponseEntity.ok(updatedStatement);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteStatement(@PathVariable Long id) {
        if (statementService.getStatementById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        statementService.deleteStatement(id);
        return ResponseEntity.noContent().build();
    }
}
