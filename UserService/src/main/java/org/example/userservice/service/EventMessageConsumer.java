// UserService/src/main/java/org/example/userservice/listener/EventMessageConsumer.java
package org.example.userservice.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class EventMessageConsumer {

    @RabbitListener(queues = "event-queue")
    public String processEventMessage(String message) {
        System.out.println("User Service received message: " + message);
        return "User Service processed: " + message;
    }
}