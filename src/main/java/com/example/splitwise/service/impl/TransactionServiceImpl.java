package com.example.splitwise.service.impl;

import com.example.splitwise.domain.Group;
import com.example.splitwise.domain.Transaction;
import com.example.splitwise.domain.User;
import com.example.splitwise.dto.request.TransactionRequest;
import com.example.splitwise.exception.NotFoundException;
import com.example.splitwise.repository.TransactionRepository;
import com.example.splitwise.service.GroupService;
import com.example.splitwise.service.TransactionService;
import com.example.splitwise.service.UserBalanceService;
import com.example.splitwise.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private GroupService groupService;

    @Autowired
    private UserService userService;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserBalanceService userBalanceService;

    @Override
    public Transaction getTransactionById(long id) throws NotFoundException {
        Optional<Transaction> transactionOptional =  transactionRepository.findById(id);
        if(transactionOptional.isEmpty()) {
            throw new NotFoundException("Transaction id: " + id + " not found");
        }
        return transactionOptional.orElse(null);
    }

    @Override
    @Transactional
    public Transaction createTransaction(TransactionRequest transactionRequest) throws NotFoundException {
        Transaction transaction = new Transaction();

        Group group = groupService.getGroupById(transactionRequest.getGroupId());
        User payer = userService.getUserById(transactionRequest.getPayerId());
        User payee = userService.getUserById(transactionRequest.getPayeeId());
        validateUser(payer, group);
        validateUser(payee, group);

        transaction.setDescription(transactionRequest.getDescription());
        transaction.setAmount(transactionRequest.getAmount());
        transaction.setPayer(payer);
        transaction.setPayee(payee);
        transaction.setGroup(group);

        userBalanceService.updateUserBalance(payer, payee, transactionRequest.getAmount(), group);

        return transactionRepository.save(transaction);
    }

    public void validateUser(User user, Group group) throws NotFoundException {
        if(!group.getGroupUsers().contains(user)) {
            throw new NotFoundException("User: " + user.getFirstName() + " is not part of group: " + group.getId());
        }
    }
}
