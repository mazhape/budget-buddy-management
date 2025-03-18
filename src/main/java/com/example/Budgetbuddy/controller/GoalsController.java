package com.example.Budgetbuddy.controller;

import com.example.Budgetbuddy.model.Goal;
import com.example.Budgetbuddy.repository.GoalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/goals")
public class GoalsController {
    @Autowired
    private GoalRepository goalRepository;

    @PostMapping("/goals")
    public ResponseEntity<String> setGoal(@RequestBody Goal goal) {
        goalRepository.save(goal);
        return ResponseEntity.ok("Goal set successfully");
    }

    @GetMapping("/goals/{userId}")
    public ResponseEntity<List<Goal>> getGoals(@PathVariable Long userId) {
        List<Goal> goals = goalRepository.findByUserId(userId);
        return ResponseEntity.ok(goals);
    }
}
