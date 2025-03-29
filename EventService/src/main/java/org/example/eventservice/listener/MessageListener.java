package org.example.eventservice.listener;

import org.example.eventservice.model.Event;
import org.example.eventservice.repo.EventRepository;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class MessageListener {

    @Autowired
    private EventRepository eventRepository;

    @RabbitListener(queues = "event.queue")
    public String handleMessage(Message message) {
        String received = new String(message.getBody());
        System.out.println("Received message: " + received);

        // Extract username from the message (e.g., "Create welcome event for user: username")
        String username = received.split("user: ")[1];
        // Create a welcome event
        Event welcomeEvent = new Event(UUID.randomUUID().toString(), "Welcome Event for " + username, username);
        eventRepository.save(welcomeEvent);

        return "Created welcome event for user: " + username;
    }
}