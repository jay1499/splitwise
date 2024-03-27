package com.example.splitwise.service.impl;

import com.example.splitwise.domain.Group;
import com.example.splitwise.domain.User;
import com.example.splitwise.domain.UserBalance;
import com.example.splitwise.repository.UserBalanceRepository;
import com.example.splitwise.service.UserBalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserBalanceServiceImpl implements UserBalanceService {

    @Autowired
    private UserBalanceRepository userBalanceRepository;

    public void updateUserBalance(User payer, User payee, double amount, Group group) {
        UserBalance userBalance = userBalanceRepository.findByPayerAndPayeeAndGroup(payer, payee, group);
        if (userBalance == null) {
            userBalance = new UserBalance();
            userBalance.setPayer(payer);
            userBalance.setPayee(payee);
            userBalance.setGroup(group);
        }
        userBalance.setAmount(userBalance.getAmount() + amount);
        userBalanceRepository.save(userBalance);
    }

    public List<UserBalance> getAllUserBalancesByGroup(Group group) {
        List<UserBalance> userBalances = userBalanceRepository.findByGroup(group);
        return userBalances;
    }
}
