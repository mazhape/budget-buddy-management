package com.example.Budgetbuddy.controller;

import com.example.Budgetbuddy.service.BudgetService;
import com.example.Budgetbuddy.service.InvestecApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api/budgets")
public class BudgetController {
    @Autowired
    private BudgetService budgetService;

    @Autowired
    private InvestecApiService investecApiService;

    @PostMapping("/update/{userId}")
    public ResponseEntity<String> updateSpending(@PathVariable String userId) {
        budgetService.updateSpending(userId);
        return ResponseEntity.ok("Spending updated successfully");
    }

    @GetMapping("/insights")
    public ResponseEntity<Map<String, Object>> getInsights() {
        Map<String, Object> insights = new HashMap<>();
        insights.put("totalSpent", investecApiService.calculateTotalSpent());
        insights.put("topCategories", investecApiService.getTopSpendingCategories());
        insights.put("monthlyTrends", investecApiService.getMonthlySpendingTrends());
        return ResponseEntity.ok(insights);
    }
}
