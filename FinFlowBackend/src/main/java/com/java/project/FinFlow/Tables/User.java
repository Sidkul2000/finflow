package com.java.project.FinFlow.Tables;


import jakarta.persistence.*;

import java.util.Set;

import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"com.java.project.FinFlow.Tables"})

@Entity
@Table(name = "User")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "budget", nullable = false, columnDefinition = "FLOAT DEFAULT 0")
    private Float budget = 0.0f;

    @Column(name = "password", nullable = false)
    private String password;
    
    @OneToMany(mappedBy="user")
    private Set<Records> records;

    // Constructors
    public User() {
    }

    public User(String name, String email, Float budget, String password) {
        this.name = name;
        this.email = email;
        this.budget = budget;
        this.password = password;
    }

    // Getters and setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Float getBudget() {
        return budget;
    }

    public void setBudget(Float budget) {
        this.budget = budget;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", budget=" + budget +
                ", password='[PROTECTED]'" +
                '}';
    }
}