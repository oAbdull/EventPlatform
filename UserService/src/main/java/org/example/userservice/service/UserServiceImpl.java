package org.example.userservice.service;

import org.example.userservice.model.User;
import org.example.userservice.repo.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public User createUser(User user) {
        User savedUser = userRepository.save(user);
        logger.info("Created user: {}", savedUser.getUsername());

        String message = "Create welcome event for user: " + savedUser.getUsername();
        logger.info("Sending message to EventService: {}", message);
        String reply = (String) rabbitTemplate.convertSendAndReceive("event.exchange", "event.routingkey", message);
        if (reply != null) {
            logger.info("Received reply from EventService: {}", reply);
        } else {
            logger.warn("No reply received from EventService");
        }

        return savedUser;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}