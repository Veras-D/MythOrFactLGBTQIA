package com.veras.mythOrFactLGBT.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "statements")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Statement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String statement;

    @Column(name = "is_fact", nullable = false)
    private boolean isFact;

    @Lob
    @Column(nullable = false, columnDefinition = "TEXT")
    private String explanation;

    @Column(nullable = false)
    private Integer difficulty;

    @Column(nullable = false, length = 50)
    private String category;
}
