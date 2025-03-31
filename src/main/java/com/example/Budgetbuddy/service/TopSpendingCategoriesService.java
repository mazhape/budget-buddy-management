package com.example.Budgetbuddy.service;

import com.example.Budgetbuddy.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TopSpendingCategoriesService {

    @Autowired
    private InvestecApiService investecApiService;

    @Autowired
    private ExpenseCategorizationService categorizationService;

    public Map<String, BigDecimal> getTopSpendingCategories(String accountId) {
        List<Transaction> transactions = investecApiService.fetchTransactions(accountId);
        return categorizationService.categorizeTransactions(transactions);
    }
}