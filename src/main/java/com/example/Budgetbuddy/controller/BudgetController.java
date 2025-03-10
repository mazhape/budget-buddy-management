package com.example.Budgetbuddy.controller;

import com.example.Budgetbuddy.service.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/budgets")
public class BudgetController {
    @Autowired
    private BudgetService budgetService;

    @PostMapping("/update/{userId}")
    public ResponseEntity<String> updateSpending(@PathVariable String userId) {
        budgetService.updateSpending(userId);
        return ResponseEntity.ok("Spending updated successfully");
    }
}
