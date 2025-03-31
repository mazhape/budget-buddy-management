package com.example.Budgetbuddy.controller;

import com.example.Budgetbuddy.service.BudgetService;
import com.example.Budgetbuddy.service.InvestecApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/budgets")
public class BudgetController {

    private static final Logger logger = LoggerFactory.getLogger(BudgetController.class);

    @Autowired
    private BudgetService budgetService;

    @Autowired
    private InvestecApiService investecApiService;

    @PostMapping("/update/{userId}")
    public ResponseEntity<String> updateSpending(@PathVariable String userId, @RequestBody Map<String, Object> budgetRequest) {
        logger.info("Received budget update request for user: {}", userId);
        logger.info("Request Body: {}", budgetRequest);

        try {
            // Log the transaction before updating spending
            logger.info("Logging transactions for user: {}", userId);
            // Assuming that transactions are fetched in the service layer, log them here:
            budgetService.updateSpending(userId);
            logger.info("Spending updated successfully for user: {}", userId);
            return ResponseEntity.ok("Spending updated successfully");
        } catch (Exception e) {
            logger.error("Error while updating spending for user: {}", userId, e);
            return ResponseEntity.status(500).body("Error updating spending");
        }
    }

    @GetMapping("/insights")
    public ResponseEntity<Map<String, Object>> getInsights() {
        logger.info("Fetching insights");

        Map<String, Object> insights = new HashMap<>();
        insights.put("totalSpent", investecApiService.calculateTotalSpent());
        insights.put("topCategories", investecApiService.getTopSpendingCategories());
        insights.put("monthlyTrends", investecApiService.getMonthlySpendingTrends());

        logger.info("Insights: {}", insights);
        return ResponseEntity.ok(insights);
    }
    @PostMapping
    public void setBudget(@RequestParam String userId, @RequestParam String category, @RequestParam BigDecimal limit) {
        budgetService.setBudget(userId, category, limit);
    }

    @GetMapping
    public Map<String, BigDecimal> getBudgets(@RequestParam String userId) {
        return budgetService.getBudgets(userId);
    }
}
