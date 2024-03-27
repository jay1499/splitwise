package com.example.splitwise.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Table(name = "groups")
@Entity
@AttributeOverride(name = "id", column = @Column(name = "group_id"))
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class Group extends BaseEntity {

    @Column(name = "group_name")
    private String groupName;

    @Column(name = "description")
    private String description;

    @ManyToMany
    @JsonManagedReference
    @JoinTable(
            name = "user_group",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> groupUsers;
}
