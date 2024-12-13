package com.java.project.FinFlow.models;

public class User {

    private Long userId;

    private String name;

    private String email;

    private Float budget = 0.0f;

    private String password;
    
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