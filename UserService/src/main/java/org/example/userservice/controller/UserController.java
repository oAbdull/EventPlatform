package org.example.userservice.controller;

import org.example.userservice.model.User;
import org.example.userservice.service.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserServiceImpl userService;

    @PostMapping
    public User createUser(@RequestBody User user) {
        logger.info("Received request to create user: {}", user.getUsername());
        return userService.createUser(user);
    }

    @GetMapping
    public List<User> getAllUsers() {
        logger.info("Received request to retrieve all users");
        return userService.getAllUsers();
    }

    @GetMapping("/test")
    public String processTest() {
        logger.info("Processing test request");
        return "UserService is up and running!";
    }
}