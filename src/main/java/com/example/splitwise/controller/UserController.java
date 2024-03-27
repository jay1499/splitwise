package com.example.splitwise.controller;

import com.example.splitwise.domain.User;
import com.example.splitwise.dto.request.UserRequest;
import com.example.splitwise.exception.NotFoundException;
import com.example.splitwise.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public User createUser(@RequestBody @Validated UserRequest userRequest) {
        return userService.createUser(userRequest);
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable(value = "id") long id) throws NotFoundException {
        return userService.getUserById(id);
    }
}
