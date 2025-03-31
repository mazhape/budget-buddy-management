package com.example.Budgetbuddy.service;

import com.example.Budgetbuddy.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SpendingAlertsService {

    @Autowired
    private InvestecApiService investecApiService;

    @Autowired
    private ExpenseCategorizationService categorizationService;

    public Map<String, String> getSpendingAlerts(String accountId, Map<String, BigDecimal> budgetLimits) {
        List<Transaction> transactions = investecApiService.fetchTransactions(accountId);
        Map<String, BigDecimal> spendingByCategory = categorizationService.categorizeTransactions(transactions);

        Map<String, String> alerts = new HashMap<>();

        for (Map.Entry<String, BigDecimal> entry : spendingByCategory.entrySet()) {
            String category = entry.getKey();
            BigDecimal spent = entry.getValue();
            BigDecimal limit = budgetLimits.getOrDefault(category, BigDecimal.ZERO);

            if (spent.compareTo(limit) > 0) {
                alerts.put(category, "You've exceeded your budget for " + category + " by $" + spent.subtract(limit));
            }
        }

        return alerts;
    }
}