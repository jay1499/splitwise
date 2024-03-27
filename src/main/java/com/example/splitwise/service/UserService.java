package com.example.splitwise.service;

import com.example.splitwise.domain.User;
import com.example.splitwise.dto.request.UserRequest;
import com.example.splitwise.exception.NotFoundException;

public interface UserService {

    User getUserById(long id) throws NotFoundException;

    User createUser(UserRequest userRequest);
}
