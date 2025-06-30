package com.veras.mythOrFactLGBT.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

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
    @JdbcTypeCode(SqlTypes.LONG32VARCHAR)
    @Column(nullable = false, columnDefinition = "TEXT")
    private String explanation;

    @Column(nullable = false)
    private Integer difficulty;

    @Column(nullable = false, length = 50)
    private String category;
}
