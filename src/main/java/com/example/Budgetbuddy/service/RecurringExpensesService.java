package com.example.Budgetbuddy.service;

import com.example.Budgetbuddy.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class RecurringExpensesService {

    @Autowired
    private InvestecApiService investecApiService;

    public Map<String, BigDecimal> detectRecurringExpenses(String accountId) {
        List<Transaction> transactions = investecApiService.fetchTransactions(accountId);
        Map<String, BigDecimal> recurringExpenses = new HashMap<>();

        // Group transactions by description and sum amounts
        for (Transaction transaction : transactions) {
            String description = transaction.getDescription();
            BigDecimal amount = transaction.getAmount();

            recurringExpenses.put(description, recurringExpenses.getOrDefault(description, BigDecimal.ZERO).add(amount));
        }

        // Filter out non-recurring expenses (e.g., one-time payments)
        recurringExpenses.entrySet().removeIf(entry -> entry.getValue().compareTo(BigDecimal.valueOf(50)) < 0);

        return recurringExpenses;
    }
}