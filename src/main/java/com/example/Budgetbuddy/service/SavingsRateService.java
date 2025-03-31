package com.example.Budgetbuddy.service;

import com.example.Budgetbuddy.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class SavingsRateService {

    @Autowired
    private InvestecApiService investecApiService;

    public BigDecimal calculateSavingsRate(String accountId) {
        List<Transaction> transactions = investecApiService.fetchTransactions(accountId);

        BigDecimal totalIncome = transactions.stream()
                .filter(t -> t.getType().equalsIgnoreCase("CREDIT"))
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalExpenses = transactions.stream()
                .filter(t -> t.getType().equalsIgnoreCase("DEBIT"))
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (totalIncome.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }

        return ((totalIncome.subtract(totalExpenses)).divide(totalIncome, 2, BigDecimal.ROUND_HALF_UP)).multiply(BigDecimal.valueOf(100));
    }
}