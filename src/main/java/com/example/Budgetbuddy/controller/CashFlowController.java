package com.example.Budgetbuddy.controller;

import com.example.Budgetbuddy.service.CashFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/api/cash-flow")
public class CashFlowController {

    @Autowired
    private CashFlowService cashFlowService;

    @GetMapping
    public Map<String, Map<String, BigDecimal>> getCashFlow(@RequestParam String accountId) {
        return cashFlowService.getCashFlow(accountId);
    }
}