package com.example.splitwise.domain;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "user_balance")
@Entity
@AttributeOverride(name = "id", column = @Column(name = "user_balance_id"))
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class UserBalance extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "payer")
    private User payer;

    @ManyToOne
    @JoinColumn(name = "payee")
    private User payee;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    private double amount;
}
