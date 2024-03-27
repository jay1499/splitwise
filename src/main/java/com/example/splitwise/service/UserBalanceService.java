package com.example.splitwise.service;

import com.example.splitwise.domain.Group;
import com.example.splitwise.domain.User;
import com.example.splitwise.domain.UserBalance;

import java.util.List;

public interface UserBalanceService {
    void updateUserBalance(User payer, User payee, double amount, Group group);

    List<UserBalance> getAllUserBalancesByGroup(Group group);
}
