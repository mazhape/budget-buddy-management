package com.example.Budgetbuddy.service;

import com.example.Budgetbuddy.model.Goal;
import com.example.Budgetbuddy.model.Transaction;
import com.example.Budgetbuddy.repository.GoalRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class GoalProgressService {
    private static final Logger logger = LoggerFactory.getLogger(GoalProgressService.class);

    @Autowired
    private InvestecApiService investecApiService;

    @Autowired
    private GoalRepository goalRepository;

    public void updateGoalProgress(Long userId) {
        String accountId = getAccountIdFromUserId(userId); // Fetch the account ID

        if (accountId == null) {
            throw new IllegalArgumentException("No account ID found for user: " + userId);
        }

        try {
            List<Transaction> transactions = investecApiService.fetchTransactions(accountId);
            List<Goal> goals = goalRepository.findByUserId(userId);

            for (Goal goal : goals) {
                double progress = calculateProgress(goal, transactions);
                BigDecimal progressValue = BigDecimal.valueOf(progress);
                goal.setCurrentAmount(progressValue);
                goalRepository.save(goal);
                logger.info("Updated progress for goal ID {}: {}", goal.getId(), progressValue);
            }
        } catch (Exception e) {
            logger.error("Error updating goal progress for user ID {}: {}", userId, e.getMessage(), e);
            throw new RuntimeException("Failed to update goal progress", e);
        }
    }

    // Placeholder method for fetching account ID
    private String getAccountIdFromUserId(Long userId) {
        // You need to implement this method to retrieve the correct account ID from your DB or API
        return "3353431574710166878182963"; // Replace with actual logic
    }

    private double calculateProgress(Goal goal, List<Transaction> transactions) {
        if (goal == null || transactions == null || transactions.isEmpty()) {
            logger.warn("Invalid input for progress calculation: goal={}, transactions={}", goal, transactions);
            return 0.0;
        }

        try {
            if (goal.getType().equalsIgnoreCase("savings")) {
                return transactions.stream()
                        .filter(t -> t.getType().equalsIgnoreCase("deposit"))
                        .mapToDouble(t -> t.getAmount().doubleValue())
                        .sum();
            } else if (goal.getType().equalsIgnoreCase("debt")) {
                return transactions.stream()
                        .filter(t -> t.getType().equalsIgnoreCase("payment"))
                        .mapToDouble(t -> t.getAmount().doubleValue())
                        .sum();
            } else {
                logger.warn("Unknown goal type: {}", goal.getType());
                return 0.0;
            }
        } catch (Exception e) {
            logger.error("Error calculating progress for goal ID {}: {}", goal.getId(), e.getMessage(), e);
            return 0.0;
        }
    }
}