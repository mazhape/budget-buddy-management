package com.example.Budgetbuddy.controller;

import com.example.Budgetbuddy.model.Transaction;
import com.example.Budgetbuddy.service.InvestecApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private InvestecApiService investecApiService;

    @GetMapping("/{accountId}")
    public List<Transaction> getTransactions(@PathVariable String accountId) {
        return investecApiService.fetchTransactions(accountId);
    }
}