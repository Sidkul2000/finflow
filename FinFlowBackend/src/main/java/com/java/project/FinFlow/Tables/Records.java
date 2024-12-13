package com.java.project.FinFlow.Tables;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "records")
public class Records {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long record_id;
	private Double amount;
    private LocalDateTime timestamp;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Categories category;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Records() {
    }

    public Records(Double amount, LocalDateTime timestamp, Categories category, User user) {
        this.amount = amount;
        this.timestamp = timestamp;
        this.category = category;
        this.user = user;
    }
    
    public Long getRecord_id() {
		return record_id;
	}

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Categories getCategory() {
        return category;
    }

    public void setCategory(Categories category) {
        this.category = category;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
