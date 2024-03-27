package com.example.splitwise.service.impl;

import com.example.splitwise.domain.Expense;
import com.example.splitwise.domain.ExpenseType;
import com.example.splitwise.domain.Group;
import com.example.splitwise.domain.User;
import com.example.splitwise.dto.request.ExpenseRequest;
import com.example.splitwise.exception.NotFoundException;
import com.example.splitwise.repository.ExpenseRepository;
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

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ExpenseServiceImplTest {

    @Mock
    private ExpenseRepository expenseRepository;

    @Mock
    private GroupService groupService;

    @Mock
    private UserBalanceService userBalanceService;

    @Mock
    private UserService userService;

    @InjectMocks
    private ExpenseServiceImpl expenseService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("When new expense is created successfully")
    public void testCreateExpense_EqualExpense() throws NotFoundException {
        ExpenseRequest expenseRequest = new ExpenseRequest();
        expenseRequest.setGroupId(1L);
        expenseRequest.setPayerId(1L);
        expenseRequest.setAmount(100);
        expenseRequest.setExpenseType(ExpenseType.EQUAL);

        Group group = new Group();
        group.setId(1);
        group.setGroupUsers(new ArrayList<>());

        User payer = new User();
        payer.setId(1);

        User user2 = new User();
        group.setGroupUsers(List.of(payer, user2));

        Expense expense = new Expense();
        expense.setExpenseType(ExpenseType.EQUAL);
        expense.setAmount(100);
        expense.setGroup(group);
        expense.setPayer(payer);

        when(groupService.getGroupById(expenseRequest.getGroupId())).thenReturn(group);
        when(userService.getUserById(expenseRequest.getPayerId())).thenReturn(payer);
        when(expenseRepository.save(any())).thenReturn(expense);

        Expense result = expenseService.createExpense(expenseRequest);

        assertNotNull(result);
        assertEquals(expenseRequest.getAmount(), result.getAmount());
        assertEquals(expenseRequest.getGroupId(), result.getGroup().getId());
        assertEquals(expenseRequest.getPayerId(), result.getPayer().getId());

        verify(expenseRepository, times(1)).save(any(Expense.class));
        verify(userBalanceService, times(1)).updateUserBalance(any(User.class), any(User.class), anyDouble(), any(Group.class));
    }


    @Test
    @DisplayName("Finding all expenses by group Id")
    public void testFindExpensesByGroup() {
        Group group = new Group();
        group.setId(1);

        List<Expense> expenses = new ArrayList<>();
        when(expenseRepository.findByGroup(group)).thenReturn(expenses);

        List<Expense> result = expenseService.findExpensesByGroup(group);

        assertNotNull(result);
        assertEquals(expenses, result);
    }
}
