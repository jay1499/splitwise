package com.example.splitwise.controller;

import com.example.splitwise.domain.Transaction;
import com.example.splitwise.dto.request.TransactionRequest;
import com.example.splitwise.exception.NotFoundException;
import com.example.splitwise.service.TransactionService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/{id}")
    public Transaction getTransactionById(@PathVariable(value = "id") long id) throws NotFoundException {
        return transactionService.getTransactionById(id);
    }

    @PostMapping
    @ApiOperation(value = "Create a new transaction", notes = "This endpoint creates a new transaction between a payer and a payee belonging to a particular group.")
    public Transaction createTransaction(@RequestBody @Validated TransactionRequest transactionRequest) throws NotFoundException {
        return transactionService.createTransaction(transactionRequest);
    }
}
