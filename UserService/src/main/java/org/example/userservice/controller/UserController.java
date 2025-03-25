package org.example.userservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/test")
    public String processTest() {
        // Send a message to RabbitMQ and wait for a reply
        String message = "Hello from UserService";
        logger.info("Sending message to RabbitMQ: {}", message);
        String reply = (String) rabbitTemplate.convertSendAndReceive("test.exchange", "test.routingkey", message);
        // Log the reply received from EventService
        if (reply != null) {
            logger.info("Received reply from EventService: {}", reply);
        } else {
            logger.warn("No reply received from EventService");
        }
        return reply != null ? reply : "No reply received";
    }
}