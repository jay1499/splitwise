package com.example.splitwise.service;

import com.example.splitwise.domain.Expense;
import com.example.splitwise.domain.Group;
import com.example.splitwise.dto.request.ExpenseRequest;
import com.example.splitwise.exception.NotFoundException;

import java.util.List;

public interface ExpenseService {
    Expense getExpenseById(long expenseId) throws NotFoundException;

    Expense createExpense(ExpenseRequest expenseRequest) throws NotFoundException;

    List<Expense> findExpensesByGroup(Group group);

}
