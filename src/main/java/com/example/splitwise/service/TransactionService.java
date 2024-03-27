package com.example.splitwise.service;

import com.example.splitwise.domain.Transaction;
import com.example.splitwise.dto.request.TransactionRequest;
import com.example.splitwise.exception.NotFoundException;

public interface TransactionService {
    Transaction getTransactionById(long transactionId) throws NotFoundException;

    Transaction createTransaction(TransactionRequest transactionRequest) throws NotFoundException;
}
