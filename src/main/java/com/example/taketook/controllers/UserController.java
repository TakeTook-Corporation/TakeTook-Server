package com.example.taketook.controllers;

import com.example.taketook.entity.User;
import com.example.taketook.payload.request.CreateUserRequest;
import com.example.taketook.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/create")
    public User createUser(@RequestBody CreateUserRequest createUserRequest) {
        User user = new User(createUserRequest.getName(), createUserRequest.getSurname(), createUserRequest.getPhone(), createUserRequest.getAddress(), createUserRequest.getCity());
        return userRepository.save(user);
    }
}
