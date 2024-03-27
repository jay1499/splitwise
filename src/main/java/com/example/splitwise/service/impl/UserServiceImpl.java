package com.example.splitwise.service.impl;

import com.example.splitwise.domain.User;
import com.example.splitwise.dto.request.UserRequest;
import com.example.splitwise.exception.NotFoundException;
import com.example.splitwise.repository.UserRepository;
import com.example.splitwise.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    public User createUser(UserRequest userRequest) {
        User user = new User();
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        return userRepository.save(user);
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable long id) throws NotFoundException {
        Optional<User> userOptional =  userRepository.findById(id);
        if(userOptional.isEmpty()) {
            throw new NotFoundException("User id: " + id + " not found");
        }
        return userOptional.orElse(null);
    }

}
