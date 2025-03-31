package com.example.Budgetbuddy.controller;

import com.example.Budgetbuddy.service.SavingsRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/savings-rate")
public class SavingsRateController {

    @Autowired
    private SavingsRateService savingsRateService;

    @GetMapping
    public BigDecimal getSavingsRate(@RequestParam String accountId) {
        return savingsRateService.calculateSavingsRate(accountId);
    }
}