package com.example.splitwise.repository;

import com.example.splitwise.domain.Group;
import com.example.splitwise.domain.User;
import com.example.splitwise.domain.UserBalance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserBalanceRepository extends JpaRepository<UserBalance, Long> {

    UserBalance findByPayerAndPayeeAndGroup(User payer, User payee, Group group);

    List<UserBalance> findByGroup(Group group);
}
