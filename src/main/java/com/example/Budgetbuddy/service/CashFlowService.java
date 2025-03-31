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
public class CashFlowService {

    @Autowired
    private InvestecApiService investecApiService;

    public Map<String, Map<String, BigDecimal>> getCashFlow(String accountId) {
        List<Transaction> transactions = investecApiService.fetchTransactions(accountId);
        Map<String, Map<String, BigDecimal>> cashFlow = new HashMap<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");

        for (Transaction transaction : transactions) {
            String month = LocalDate.parse(transaction.getTransactionDate()).format(formatter);
            String type = transaction.getType().equalsIgnoreCase("CREDIT") ? "Income" : "Expense";
            BigDecimal amount = transaction.getAmount();

            cashFlow.computeIfAbsent(month, k -> new HashMap<>());
            cashFlow.get(month).put(type, cashFlow.get(month).getOrDefault(type, BigDecimal.ZERO).add(amount));
        }

        return cashFlow;
    }
}