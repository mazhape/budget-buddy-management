package com.example.Budgetbuddy.controller;

import com.example.Budgetbuddy.service.RecurringExpensesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/api/recurring-expenses")
public class RecurringExpensesController {

    @Autowired
    private RecurringExpensesService recurringExpensesService;

    @GetMapping
    public Map<String, BigDecimal> getRecurringExpenses(@RequestParam String accountId) {
        return recurringExpensesService.detectRecurringExpenses(accountId);
    }
}