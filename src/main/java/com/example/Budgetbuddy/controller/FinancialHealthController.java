package com.example.Budgetbuddy.controller;

import com.example.Budgetbuddy.service.FinancialHealthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/financial-health")
public class FinancialHealthController {

    @Autowired
    private FinancialHealthService financialHealthService;

    @GetMapping
    public int getFinancialHealthScore(@RequestParam String accountId) {
        return financialHealthService.calculateFinancialHealthScore(accountId);
    }
}