package com.example.Budgetbuddy.service;

import com.example.Budgetbuddy.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class DebtService {

    @Autowired
    private InvestecApiService investecApiService;

    public String getDebtRepaymentPlan(String accountId) {
        List<Transaction> transactions = investecApiService.fetchTransactions(accountId);

        BigDecimal totalDebt = transactions.stream()
                .filter(t -> t.getDescription().toLowerCase().contains("loan") || t.getDescription().toLowerCase().contains("debt"))
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (totalDebt.compareTo(BigDecimal.ZERO) > 0) {
            return "You have a total debt of $" + totalDebt + ". Consider paying $500 per month to clear it in " + (totalDebt.divide(BigDecimal.valueOf(500), 0, BigDecimal.ROUND_UP)) + " months.";
        } else {
            return "No debt detected.";
        }
    }
}