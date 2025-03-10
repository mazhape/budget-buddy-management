package com.example.Budgetbuddy.service;


import com.example.Budgetbuddy.entity.Budget;
import com.example.Budgetbuddy.model.Transaction;
import com.example.Budgetbuddy.repository.BudgetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
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
                        Collectors.summingDouble(Transaction::getAmount)
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
}