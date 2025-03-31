package com.example.Budgetbuddy.controller;

import com.example.Budgetbuddy.service.BudgetTrackerService;
import com.example.Budgetbuddy.service.BudgetSuggestionService; // Import the missing service
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/budget")
public class BudgetTrackerController {

    @Autowired
    private BudgetTrackerService budgetTrackerService;

    @Autowired
    private BudgetSuggestionService budgetSuggestionService; // Inject the missing service

    /**
     * Get spending by category for a specific account.
     */
    @GetMapping("/spending")
    public Map<String, BigDecimal> getSpendingByCategory(@RequestParam String accountId) {
        return budgetTrackerService.getSpendingByCategory(accountId);
    }

    /**
     * Get total spending for a specific account.
     */
    @GetMapping("/total-spending")
    public BigDecimal getTotalSpending(@RequestParam String accountId) {
        return budgetTrackerService.getTotalSpending(accountId);
    }
    /**
     * Get budget suggestions for a specific account.
     */
    @GetMapping("/suggestions")
    public Map<String, String> getBudgetSuggestions(
            @RequestParam String accountId,
            @RequestParam Map<String, String> budgetLimits) {

        // Convert budget limits to BigDecimal
        Map<String, BigDecimal> limits = new HashMap<>();
        for (Map.Entry<String, String> entry : budgetLimits.entrySet()) {
            limits.put(entry.getKey(), new BigDecimal(entry.getValue()));
        }

        Map<String, BigDecimal> spendingByCategory = budgetTrackerService.getSpendingByCategory(accountId);
        return budgetSuggestionService.getBudgetSuggestions(spendingByCategory, limits);
    }
}