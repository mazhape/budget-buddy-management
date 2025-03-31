package com.example.Budgetbuddy.service;

import com.example.Budgetbuddy.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
public class BudgetTrackerService {

    @Autowired
    private InvestecApiService investecApiService;

    @Autowired
    private ExpenseCategorizationService categorizationService;

    /**
     * Fetch transactions and categorize them to calculate spending by category.
     */
    public Map<String, BigDecimal> getSpendingByCategory(String accountId) {
        List<Transaction> transactions = investecApiService.fetchTransactions(accountId);
        return categorizationService.categorizeTransactions(transactions);
    }

    /**
     * Calculate total spending across all categories.
     */
    public BigDecimal getTotalSpending(String accountId) {
        Map<String, BigDecimal> spendingByCategory = getSpendingByCategory(accountId);
        return spendingByCategory.values().stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}