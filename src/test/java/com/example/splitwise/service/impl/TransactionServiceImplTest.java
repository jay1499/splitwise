package com.example.splitwise.service.impl;

import com.example.splitwise.domain.Group;
import com.example.splitwise.domain.Transaction;
import com.example.splitwise.domain.User;
import com.example.splitwise.dto.request.TransactionRequest;
import com.example.splitwise.exception.NotFoundException;
import com.example.splitwise.repository.TransactionRepository;
import com.example.splitwise.service.GroupService;
import com.example.splitwise.service.UserBalanceService;
import com.example.splitwise.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@SpringBootTest
public class TransactionServiceImplTest {

    @Mock
    private GroupService groupService;

    @Mock
    private UserService userService;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private UserBalanceService userBalanceService;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("When transaction actually exists")
    public void testGetTransactionById_ExistingId() throws NotFoundException {
        long transactionId = 1L;
        Transaction transaction = new Transaction();
        when(transactionRepository.findById(transactionId)).thenReturn(Optional.of(transaction));
        Transaction result = transactionService.getTransactionById(transactionId);
        assertNotNull(result);
        assertEquals(transaction, result);
    }

    @Test
    @DisplayName("When transaction does not exist")
    public void testGetTransactionById_NonExistingId() {
        long nonExistingId = 999L;
        when(transactionRepository.findById(nonExistingId)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> transactionService.getTransactionById(nonExistingId));
    }

    @Test
    @DisplayName("When new transaction is created successfully")
    public void testCreateTransaction_Success() throws NotFoundException {
        TransactionRequest request = new TransactionRequest();
        request.setGroupId(1L);
        request.setPayerId(1L);
        request.setPayeeId(2L);
        request.setDescription("Test transaction");
        request.setAmount(100.0);

        Group group = new Group();
        User payer = new User();
        User payee = new User();
        group.setGroupUsers(List.of(payee, payer));
        when(groupService.getGroupById(request.getGroupId())).thenReturn(group);
        when(userService.getUserById(request.getPayerId())).thenReturn(payer);
        when(userService.getUserById(request.getPayeeId())).thenReturn(payee);
        transactionService.createTransaction(request);

        verify(transactionRepository, times(1)).save(any(Transaction.class));
        verify(userBalanceService, times(1)).updateUserBalance(payer, payee, request.getAmount(), group);
    }

    @Test
    @DisplayName("When new transaction is not created successfully")
    public void testCreateTransaction_InvalidUser() throws NotFoundException {
        TransactionRequest request = new TransactionRequest();
        request.setGroupId(1L);
        request.setPayerId(1L);
        request.setPayeeId(2L);

        Group group = new Group();
        when(groupService.getGroupById(request.getGroupId())).thenReturn(group);
        when(userService.getUserById(request.getPayerId())).thenThrow(NotFoundException.class);

        assertThrows(NotFoundException.class, () -> transactionService.createTransaction(request));
        verify(transactionRepository, never()).save(any(Transaction.class));
    }
}
