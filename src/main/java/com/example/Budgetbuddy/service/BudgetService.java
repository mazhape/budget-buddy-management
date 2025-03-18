package com.example.Budgetbuddy.service;

import com.example.Budgetbuddy.entity.Budget;
import com.example.Budgetbuddy.model.Transaction;
import com.example.Budgetbuddy.repository.BudgetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BudgetService {

    @Autowired
    private BudgetRepository budgetRepository;

    @Autowired
    private InvestecApiService investecApiService;

    /**
     * Updates the spending for a user based on their transactions.
     *
     * @param userId The ID of the user.
     */
    public void updateSpending(String userId) {
        // Fetch transactions for the user
        List<Transaction> transactions = investecApiService.fetchTransactions(userId);

        // Group transactions by category and calculate total spending per category
        Map<String, Double> spendingByCategory = transactions.stream()
                .collect(Collectors.groupingBy(
                        Transaction::getCategory,
                        Collectors.summingDouble(transaction -> transaction.getAmount().doubleValue())
                ));

        // Update budgets with the calculated spending
        spendingByCategory.forEach((category, amount) -> {
            Budget budget = budgetRepository.findByUserIdAndCategory(userId, category);
            if (budget != null) {
                budget.setSpent(amount);
                budgetRepository.save(budget);
            }
        });
    }

    /**
     * Generates alerts for a user based on their budget and spending.
     *
     * @param userId The ID of the user.
     * @return A list of alert messages.
     */
    public List<String> generateAlerts(String userId) {
        List<String> alerts = new ArrayList<>();
        List<Budget> budgets = budgetRepository.findByUserId(userId);

        for (Budget budget : budgets) {
            double remainingBudget = budget.getAllocated() - budget.getSpent();

            // Example alert: If more than 80% of the budget is spent
            if (budget.getSpent() > budget.getAllocated() * 0.8) {
                alerts.add("Warning: You've spent over 80% of your " + budget.getCategory() + " budget.");
            }

            // Example alert: If the budget is exceeded
            if (budget.getSpent() > budget.getAllocated()) {
                alerts.add("Alert: You've exceeded your " + budget.getCategory() + " budget.");
            }

            // Example alert: If the remaining budget is low
            if (remainingBudget < 100) {
                alerts.add("Info: You have less than R100 remaining in your " + budget.getCategory() + " budget.");
            }
        }

        return alerts;
    }
}