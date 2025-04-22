package com.ticketservice.services;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class ConsumerService {

    @RabbitListener(queues = {"${rabbitmq.queue.name}"})
    public void listen(String message) {
        System.out.println("Received message: " + message);
    }

}
