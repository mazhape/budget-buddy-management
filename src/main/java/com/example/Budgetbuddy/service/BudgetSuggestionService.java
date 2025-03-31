package com.example.Budgetbuddy.service;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
public class BudgetSuggestionService {

    /**
     * Compare spending against budget limits and provide suggestions.
     */
    public Map<String, String> getBudgetSuggestions(Map<String, BigDecimal> spendingByCategory, Map<String, BigDecimal> budgetLimits) {
        Map<String, String> suggestions = new HashMap<>();

        for (Map.Entry<String, BigDecimal> entry : spendingByCategory.entrySet()) {
            String category = entry.getKey();
            BigDecimal spent = entry.getValue();
            BigDecimal limit = budgetLimits.getOrDefault(category, BigDecimal.ZERO);

            if (spent.compareTo(limit) > 0) {
                BigDecimal overspend = spent.subtract(limit);
                suggestions.put(category, String.format("Reduce %s spending by $%.2f", category, overspend));
            }
        }

        return suggestions;
    }
}