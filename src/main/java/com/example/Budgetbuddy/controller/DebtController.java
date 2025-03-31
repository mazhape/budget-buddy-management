package com.example.Budgetbuddy.controller;

import com.example.Budgetbuddy.service.DebtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/debt")
public class DebtController {

    @Autowired
    private DebtService debtService;

    @GetMapping
    public String getDebtRepaymentPlan(@RequestParam String accountId) {
        return debtService.getDebtRepaymentPlan(accountId);
    }
}