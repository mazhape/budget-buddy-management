package com.example.Budgetbuddy.service;

import com.example.Budgetbuddy.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MonthlySpendingService {

    @Autowired
    private InvestecApiService investecApiService;

    public Map<String, BigDecimal> getMonthlySpending(String accountId) {
        List<Transaction> transactions = investecApiService.fetchTransactions(accountId);
        Map<String, BigDecimal> monthlySpending = new HashMap<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");

        for (Transaction transaction : transactions) {
            String month = LocalDate.parse(transaction.getTransactionDate()).format(formatter);
            BigDecimal amount = transaction.getAmount();

            monthlySpending.put(month, monthlySpending.getOrDefault(month, BigDecimal.ZERO).add(amount));
        }

        return monthlySpending;
    }
}