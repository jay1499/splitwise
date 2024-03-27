package com.example.splitwise.service.impl;

import com.example.splitwise.domain.*;
import com.example.splitwise.dto.request.GroupRequest;
import com.example.splitwise.exception.NotFoundException;
import com.example.splitwise.repository.ExpenseRepository;
import com.example.splitwise.repository.GroupRepository;
import com.example.splitwise.repository.TransactionRepository;
import com.example.splitwise.repository.UserRepository;
import com.example.splitwise.service.GroupService;
import com.example.splitwise.service.UserBalanceService;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserBalanceService userBalanceService;

    @Autowired
    private ExpenseRepository expenseRepository;

    public Group getGroupById(long id) throws NotFoundException {
        Optional<Group> groupOptional =  groupRepository.findById(id);
        if(groupOptional.isEmpty()) {
            throw new NotFoundException("Group id: " + id + " not found");
        }
        return groupOptional.orElse(null);
    }

    public Group createGroup(GroupRequest groupRequest) {
        Group group = new Group();
        List<User> userList = userRepository.findAllById(groupRequest.getUserIds());

        group.setGroupName(groupRequest.getGroupName());
        group.setDescription(groupRequest.getDescription());
        group.setGroupUsers(userList);

        return groupRepository.save(group);
    }

    @Override
    @Transactional
    public List<String> getGroupBalance(long groupId) throws NotFoundException {
        Group group = getGroupById(groupId);
        List<UserBalance> userBalances = userBalanceService.getAllUserBalancesByGroup(group);

        Map<Pair<Integer, Integer>, Double> netBalances = new HashMap<>();

        for (UserBalance userBalance : userBalances) {
            int payerId = userBalance.getPayer().getId();
            int payeeId = userBalance.getPayee().getId();
            double amount = userBalance.getAmount();

            // Update net balance for payer and payee
            Pair<Integer, Integer> pair = Pair.of(payerId, payeeId);
            netBalances.put(pair, netBalances.getOrDefault(pair, 0.0) - amount);
            netBalances.put(Pair.of(payeeId, payerId), netBalances.getOrDefault(Pair.of(payeeId, payerId), 0.0) + amount);
        }

        List<String> result = new ArrayList<>();
        for (Map.Entry<Pair<Integer, Integer>, Double> entry : netBalances.entrySet()) {
            Pair<Integer, Integer> pair = entry.getKey();
            double netBalance = entry.getValue();
            if (netBalance > 0) {
                String firstNamePayer = userRepository.findById((long) pair.getLeft()).get().getFirstName();
                String firstNamePayee = userRepository.findById((long) pair.getRight()).get().getFirstName();
                String formattedBalance = String.format("%.2f", netBalance);
                result.add(firstNamePayer + " owes " + firstNamePayee + " ₹" + formattedBalance);
            }
        }
        return result;
    }
}
