package com.example.Budgetbuddy.controller;

import com.example.Budgetbuddy.service.SpendingAlertsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/spending-alerts")
public class SpendingAlertsController {

    @Autowired
    private SpendingAlertsService spendingAlertsService;

    @GetMapping
    public Map<String, String> getSpendingAlerts(
            @RequestParam String accountId,
            @RequestParam Map<String, String> budgetLimits) {

        // Convert budget limits to BigDecimal
        Map<String, BigDecimal> limits = new HashMap<>();
        for (Map.Entry<String, String> entry : budgetLimits.entrySet()) {
            limits.put(entry.getKey(), new BigDecimal(entry.getValue()));
        }

        return spendingAlertsService.getSpendingAlerts(accountId, limits);
    }
}