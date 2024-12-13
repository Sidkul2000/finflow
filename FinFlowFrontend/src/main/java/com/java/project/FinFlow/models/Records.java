package com.java.project.FinFlow.models;

import java.time.LocalDateTime;

public class Records {

    private Long record_id;
	private Double amount;
    private LocalDateTime timestamp;
    private Categories category;
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

	@Override
	public String toString() {
		return "Records [record_id=" + record_id + ", amount=" + amount + ", timestamp=" + timestamp + ", category="
				+ category + ", user=" + user + "]";
	}
}
