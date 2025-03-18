//
//package com.example.Budgetbuddy.controller;
//
//import com.example.Budgetbuddy.service.BudgetService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/alerts")
//public class AlertController {
//
//    @Autowired
//    private BudgetService budgetService;
//
//    @GetMapping("/{userId}")
//    public List<String> getAlerts(@PathVariable String userId) {
//        return budgetService.generateAlerts(userId);
//    }
//}