package com.example.splitwise.domain;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "expenses")
@AttributeOverride(name = "id", column = @Column(name = "expense_id"))
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class Expense extends BaseEntity {
    @Column(name = "description")
    private String description;

    @Column(name = "amount")
    private double amount;

    @ManyToOne
    @JoinColumn(name = "payer_id")
    private User payer;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    @Enumerated(EnumType.STRING)
    @JoinColumn(name = "expense_type")
    private ExpenseType expenseType;

}
