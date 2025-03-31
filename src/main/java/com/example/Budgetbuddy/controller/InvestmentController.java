package com.example.Budgetbuddy.controller;

import com.example.Budgetbuddy.service.InvestmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/investment")
public class InvestmentController {

    @Autowired
    private InvestmentService investmentService;

    @GetMapping
    public String getInvestmentSuggestion(@RequestParam String accountId) {
        return investmentService.getInvestmentSuggestion(accountId);
    }
}