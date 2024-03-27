package com.example.splitwise.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.lang.NonNull;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class UserRequest implements Serializable {

    @NonNull
    private String firstName;
    private String lastName;
    @NonNull
    private String email;
}
