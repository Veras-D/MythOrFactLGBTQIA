package com.veras.mythOrFactLGBT.controller;

import com.veras.mythOrFactLGBT.model.Statement;
import com.veras.mythOrFactLGBT.service.StatementService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.context.ActiveProfiles;
import com.veras.mythOrFactLGBT.security.JwtUtil;

import java.util.Collections;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

@WebMvcTest(StatementController.class)
@AutoConfigureMockMvc(addFilters = false)
class StatementControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private StatementService statementService;

    @MockBean
    private JwtUtil jwtUtil;

    private Statement statement1;

    @BeforeEach
    void setUp() {
        statement1 = new Statement();
        statement1.setId(1L);
        statement1.setStatement("Sky is blue.");
        statement1.setFact(true);
        statement1.setExplanation("Atmospheric scattering.");
        statement1.setDifficulty(1);
        statement1.setCategory("Science");
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void createStatement_success() throws Exception {
        when(statementService.saveStatement(any(Statement.class))).thenReturn(statement1);

        mockMvc.perform(post("/api/statements")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(statement1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.statement").value("Sky is blue."));
    }

    @Test
    @WithMockUser(username = "user")
    void createStatement_forbiddenForUser() throws Exception {
        when(statementService.saveStatement(any(Statement.class))).thenReturn(statement1);
         mockMvc.perform(post("/api/statements")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(statement1)))
                .andExpect(status().isForbidden());
    }


    @Test
    @WithMockUser
    void getStatementById_success() throws Exception {
        when(statementService.getStatementById(1L)).thenReturn(Optional.of(statement1));

        mockMvc.perform(get("/api/statements/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statement").value("Sky is blue."));
    }

    @Test
    @WithMockUser
    void getStatementById_notFound() throws Exception {
        when(statementService.getStatementById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/statements/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    void getAllStatements_noFilter() throws Exception {
        when(statementService.getAllStatements()).thenReturn(List.of(statement1));
        mockMvc.perform(get("/api/statements"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].category", is("Science")));
    }

    @Test
    @WithMockUser
    void getAllStatements_withCategoryFilter() throws Exception {
        when(statementService.getStatementsByCategory("Science")).thenReturn(List.of(statement1));
        mockMvc.perform(get("/api/statements").param("category", "Science"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].category", is("Science")));
    }


    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void updateStatement_success() throws Exception {
        Statement updatedDetails = new Statement();
        updatedDetails.setStatement("Sky is often blue.");
        updatedDetails.setFact(true);
        updatedDetails.setExplanation("Rayleigh scattering.");
        updatedDetails.setDifficulty(1);
        updatedDetails.setCategory("Physics");

        // Mock finding the existing statement
        when(statementService.getStatementById(1L)).thenReturn(Optional.of(statement1));
        // Mock saving the updated statement
        when(statementService.saveStatement(any(Statement.class))).thenAnswer(invocation -> {
            Statement s = invocation.getArgument(0);
            s.setStatement(updatedDetails.getStatement());
            s.setCategory(updatedDetails.getCategory());
            return s;
        });

        mockMvc.perform(put("/api/statements/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedDetails)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statement").value("Sky is often blue."))
                .andExpect(jsonPath("$.category").value("Physics"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void deleteStatement_success() throws Exception {
        when(statementService.getStatementById(1L)).thenReturn(Optional.of(statement1));
        doNothing().when(statementService).deleteStatement(1L);

        mockMvc.perform(delete("/api/statements/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void deleteStatement_notFound() throws Exception {
        when(statementService.getStatementById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/api/statements/1"))
                .andExpect(status().isNotFound());
    }
}
