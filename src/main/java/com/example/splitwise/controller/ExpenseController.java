package com.example.splitwise.controller;

import com.example.splitwise.domain.Expense;
import com.example.splitwise.dto.request.ExpenseRequest;
import com.example.splitwise.exception.NotFoundException;
import com.example.splitwise.service.ExpenseService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/expense")
public class ExpenseController {

    @Autowired
    ExpenseService expenseService;

    @GetMapping("/{id}")
    public Expense getExpenseById(@PathVariable(value = "id") long id) throws NotFoundException {
        return expenseService.getExpenseById(id);
    }

    @PostMapping
    @ApiOperation(value = "Create a new expense in a group.", notes = "Only Equal Split is supported in the current version.")
    public Expense createExpense(@RequestBody @Validated ExpenseRequest expenseRequest) throws NotFoundException {
        return expenseService.createExpense(expenseRequest);
    }

}

