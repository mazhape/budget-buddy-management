package com.example.Budgetbuddy.model;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class Transaction {
    private String id;
    private String accountId;
    private String type;
    private String description;
    private BigDecimal amount;
    private String transactionDate;
    private String category;

    // No-argument constructor
    public Transaction() {
    }

    // Parameterized constructor
    public Transaction(String id, String accountId, String type, String description, BigDecimal amount, String transactionDate, String category) {
        this.id = id;
        this.accountId = accountId;
        this.type = type;
        this.description = description;
        this.amount = amount;
        this.transactionDate = transactionDate;
        this.category = category;
    }

    //getters
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getAccountId() {
        return accountId;
    }
    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public BigDecimal getAmount() {
        return amount;
    }
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    public String getTransactionDate() {
        return transactionDate;
    }
    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

}