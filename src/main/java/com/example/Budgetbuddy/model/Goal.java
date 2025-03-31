package com.example.Budgetbuddy.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
public class Goal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private String description;
    private BigDecimal targetAmount;
    private BigDecimal currentAmount;
    @Column(name = "goal_name")
    private String goalName;
    @Column(name = "current_progress")
    private BigDecimal currentProgress;
    @Column(name = "deadline")
    private LocalDate deadline;
    private String type;

    public Long getId() {   // <-- Explicit getter
        return id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setCurrentAmount(BigDecimal currentAmount) {
        this.currentAmount = currentAmount;
    }

    public void setCurrentProgress(BigDecimal currentProgress) {
        this.currentProgress = currentProgress;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public void setGoalName(String goalName) {
        this.goalName = goalName;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
