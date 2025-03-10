package com.example.Budgetbuddy.repository;


import com.example.Budgetbuddy.entity.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

    public interface BudgetRepository extends JpaRepository<Budget, Long> {
        Budget findByUserIdAndCategory(String userId, String category);

        List<Budget> findByUserId(String userId);
    }
