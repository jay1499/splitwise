package com.example.splitwise.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequest implements Serializable {
    private String description;

    @NonNull
    private long groupId;

    @NonNull
    private long payerId;

    @NonNull
    private long payeeId;

    @NonNull
    private double amount;
}
