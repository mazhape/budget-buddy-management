package com.example.Budgetbuddy.controller;

import com.example.Budgetbuddy.model.Debt;
import com.example.Budgetbuddy.repository.DebtRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public class DebtsController {
    @Autowired
    private DebtRepository debtRepository;

    @PostMapping("/debts")
    public ResponseEntity<String> setDebt(@RequestBody Debt debt) {
        debtRepository.save(debt);
        return ResponseEntity.ok("Debt added successfully");
    }

    @GetMapping("/debts/{userId}")
    public ResponseEntity<List<Debt>> getDebts(@PathVariable Long userId) {
        List<Debt> debts = debtRepository.findByUserId(userId);
        return ResponseEntity.ok(debts);
    }
}
