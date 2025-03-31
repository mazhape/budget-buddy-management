package com.example.Budgetbuddy.controller;

import com.example.Budgetbuddy.model.Transaction;
import com.example.Budgetbuddy.service.InvestecApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);

    @Autowired
    private InvestecApiService investecApiService;

    @GetMapping("/{accountId}")
    public ResponseEntity<?> getTransactions(@PathVariable String accountId) {
        try {
            List<Transaction> transactions = investecApiService.fetchTransactions(accountId);
            if (transactions.isEmpty()) {
                return ResponseEntity.status(HttpStatus.OK).body(Collections.emptyList()); // Return empty list with 200 status
            }
            return ResponseEntity.ok(transactions);
        } catch (Exception e) {
            logger.error("Error fetching transactions for accountId: {}", accountId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while fetching transactions.");
        }
    }

}