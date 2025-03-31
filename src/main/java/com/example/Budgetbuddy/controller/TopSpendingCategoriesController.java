package com.example.Budgetbuddy.controller;

import com.example.Budgetbuddy.service.TopSpendingCategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/api/top-spending-categories")
public class TopSpendingCategoriesController {

    @Autowired
    private TopSpendingCategoriesService topSpendingCategoriesService;

    @GetMapping
    public Map<String, BigDecimal> getTopSpendingCategories(@RequestParam String accountId) {
        return topSpendingCategoriesService.getTopSpendingCategories(accountId);
    }
}