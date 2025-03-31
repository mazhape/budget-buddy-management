package com.example.Budgetbuddy.controller;

import com.example.Budgetbuddy.service.MonthlySpendingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/api/monthly-spending")
public class MonthlySpendingController {

    @Autowired
    private MonthlySpendingService monthlySpendingService;

    @GetMapping
    public Map<String, BigDecimal> getMonthlySpending(@RequestParam String accountId) {
        return monthlySpendingService.getMonthlySpending(accountId);
    }
}