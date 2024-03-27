package com.example.splitwise.service.impl;

import com.example.splitwise.domain.*;
import com.example.splitwise.dto.request.ExpenseRequest;
import com.example.splitwise.exception.NotFoundException;
import com.example.splitwise.repository.ExpenseRepository;
import com.example.splitwise.service.ExpenseService;
import com.example.splitwise.service.GroupService;
import com.example.splitwise.service.UserBalanceService;
import com.example.splitwise.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private GroupService groupService;

    @Autowired
    private UserBalanceService userBalanceService;

    @Autowired
    private UserService userService;

    public Expense getExpenseById(long id) throws NotFoundException {
        Optional<Expense> expenseOptional =  expenseRepository.findById(id);
        if(expenseOptional.isEmpty()) {
            throw new NotFoundException("Expense id: " + id + " not found");
        }
        return expenseOptional.orElse(null);    }

    @Transactional
    public Expense createExpense(ExpenseRequest expenseRequest) throws NotFoundException {
        Expense expense = new Expense();

        Group group = groupService.getGroupById(expenseRequest.getGroupId());
        User payer = userService.getUserById(expenseRequest.getPayerId());
        ExpenseType expenseType = expenseRequest.getExpenseType();

        if(!group.getGroupUsers().contains(payer)) {
            throw new NotFoundException("User: " + payer.getFirstName() + " is not part of group: " + group.getId());
        }

        expense.setAmount(expenseRequest.getAmount());
        expense.setGroup(group);
        expense.setPayer(payer);
        expense.setExpenseType(expenseType);

        expense = expenseRepository.save(expense);

        if(ExpenseType.EQUAL == expenseType ) {
            double amountPerMember = expense.getAmount() / group.getGroupUsers().size();
            for (User user : group.getGroupUsers()) {
                if (user == payer)
                    continue;
                userBalanceService.updateUserBalance(payer, user, amountPerMember, group);
            }
        }
        //extend for other expense types
        return expense;
    }

    public List<Expense> findExpensesByGroup(Group group) {
        return expenseRepository.findByGroup(group);
    }
}
