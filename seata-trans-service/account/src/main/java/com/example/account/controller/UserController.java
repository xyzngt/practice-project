package com.example.account.controller;

import com.example.account.entity.User;
import com.example.account.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping("/users")
    public User getUser(Long userId) {
        return userService.getUserById(userId);
    }
}
