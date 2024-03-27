package com.example.splitwise.dto.request;

import com.example.splitwise.domain.ExpenseType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseRequest implements Serializable {
    private String description;

    @NonNull
    private double amount;

    @NonNull
    private long payerId;

    @NonNull
    private long groupId;

    @NonNull
    private ExpenseType expenseType;
}
