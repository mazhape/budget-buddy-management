package com.example.Budgetbuddy.service;

import com.example.Budgetbuddy.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class InvestmentService {

    @Autowired
    private InvestecApiService investecApiService;

    public String getInvestmentSuggestion(String accountId) {
        List<Transaction> transactions = investecApiService.fetchTransactions(accountId);

        BigDecimal surplus = transactions.stream()
                .filter(t -> t.getType().equalsIgnoreCase("CREDIT"))
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .subtract(transactions.stream()
                        .filter(t -> t.getType().equalsIgnoreCase("DEBIT"))
                        .map(Transaction::getAmount)
                        .reduce(BigDecimal.ZERO, BigDecimal::add));

        if (surplus.compareTo(BigDecimal.ZERO) > 0) {
            return "You have a surplus of $" + surplus + ". Consider investing in a mutual fund.";
        } else {
            return "No surplus available for investment.";
        }
    }
}