package com.example.Budgetbuddy.service;

import com.example.Budgetbuddy.model.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class ExpenseCategorizationService {

    private static final Logger logger = LoggerFactory.getLogger(ExpenseCategorizationService.class);

    // Define rules for categorizing transactions
    private static final Map<String, List<String>> CATEGORY_RULES = Map.of(
            "Groceries", List.of("supermarket", "grocery", "food"),
            "Entertainment", List.of("cinema", "netflix", "concert"),
            "Utilities", List.of("electricity", "water", "internet"),
            "Dining Out", List.of("restaurant", "cafe", "bar"),
            "Transfers", List.of("transfer", "api transfer")
    );

    /**
     * Categorize a transaction based on its description.
     */
    public String categorizeTransaction(Transaction transaction) {
        String description = transaction.getDescription().toLowerCase();
        for (Map.Entry<String, List<String>> entry : CATEGORY_RULES.entrySet()) {
            for (String keyword : entry.getValue()) {
                if (description.contains(keyword)) {
                    return entry.getKey();
                }
            }
        }
        return "Other"; // Default category
    }

    /**
     * Categorize a list of transactions and return a map of categories with their total amounts.
     */
    public Map<String, BigDecimal> categorizeTransactions(List<Transaction> transactions) {
        Map<String, BigDecimal> spendingByCategory = new HashMap<>();

        for (Transaction transaction : transactions) {
            String category = categorizeTransaction(transaction);
            BigDecimal amount = transaction.getAmount();

            // Add the amount to the category total
            spendingByCategory.put(category, spendingByCategory.getOrDefault(category, BigDecimal.ZERO).add(amount));
        }

        return spendingByCategory;
    }
}