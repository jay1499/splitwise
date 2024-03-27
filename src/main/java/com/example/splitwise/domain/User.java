package com.example.splitwise.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Table(name = "users")
@Entity
@AttributeOverride(name = "id", column = @Column(name = "user_id"))
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity {
    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @ManyToMany(mappedBy = "groupUsers")
    @JsonBackReference
    private List<Group> groups;
}
