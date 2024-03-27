package com.example.splitwise.repository;

import com.example.splitwise.domain.Group;
import com.example.splitwise.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByGroup(Group group);
}
