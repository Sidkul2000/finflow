package com.java.project.FinFlow.Tables;

// Test table, do not use!

import jakarta.persistence.*;

@Entity
@Table(name = "test_table")
public class TestTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
        return id;
    }

    public TestTable() {
    }
}
