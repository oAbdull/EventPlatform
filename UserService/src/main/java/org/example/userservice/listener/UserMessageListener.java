package org.example.userservice.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class UserMessageListener {

    @RabbitListener(queues = "event.queue") // Change from "event-queue" to "event.queue"
    public void handleMessage(String message) {
        System.out.println("Received message: " + message);
    }
}