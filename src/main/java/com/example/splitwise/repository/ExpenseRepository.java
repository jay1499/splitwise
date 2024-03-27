package com.example.splitwise.repository;

import com.example.splitwise.domain.Expense;
import com.example.splitwise.domain.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByGroup(Group group);
}
