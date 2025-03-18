package com.example.Budgetbuddy.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Transaction {
    private String id;
    private String description;
    private BigDecimal amount;
    private String category; //e.g. groceries, entertainment
    private LocalDateTime date;

    public String getCategory() {
        return category;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
