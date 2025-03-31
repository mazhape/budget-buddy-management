package com.example.Budgetbuddy.controller;

import com.example.Budgetbuddy.service.InvestecApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/balance")
public class BalanceController {

    @Autowired
    private InvestecApiService investecApiService;

    @GetMapping("/balance")
    public BigDecimal getBalance(@RequestParam String accountId) {
        return investecApiService.fetchAccountBalance(accountId);
    }

}