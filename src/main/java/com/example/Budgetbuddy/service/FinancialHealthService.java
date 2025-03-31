package com.example.Budgetbuddy.service;

import com.example.Budgetbuddy.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class FinancialHealthService {

    @Autowired
    private InvestecApiService investecApiService;

    @Autowired
    private SavingsRateService savingsRateService;

    public int calculateFinancialHealthScore(String accountId) {
        BigDecimal savingsRate = savingsRateService.calculateSavingsRate(accountId);
        List<Transaction> transactions = investecApiService.fetchTransactions(accountId);

        BigDecimal totalDebt = transactions.stream()
                .filter(t -> t.getDescription().toLowerCase().contains("loan") || t.getDescription().toLowerCase().contains("debt"))
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Example scoring formula (adjust as needed)
        int score = savingsRate.intValue() - totalDebt.intValue();
        return Math.max(0, Math.min(100, score)); // Ensure score is between 0 and 100
    }
}