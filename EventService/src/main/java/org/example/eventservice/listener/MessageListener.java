package org.example.eventservice.listener;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class MessageListener {

    @RabbitListener(queues = "test.queue")
    public String handleMessage(Message message) {
        String received = new String(message.getBody());
        System.out.println("Received message: " + received);
        return "Hardcoded reply from EventService";
    }
}