package org.example.eventservice.service;

import org.example.eventservice.config.RabbitMQConfig;
import org.example.eventservice.dto.EventCreatedMessage;
import org.example.eventservice.dto.UserEventNotification;
import org.example.eventservice.model.Event;
import org.example.eventservice.repo.EventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class EventService implements IEventService {

    private static final Logger log = LoggerFactory.getLogger(EventService.class);

    private final EventRepository repository;
    private final RabbitTemplate rabbitTemplate;

    public EventService(EventRepository repository, RabbitTemplate rabbitTemplate) {
        this.repository = repository;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public Event createEvent(Event event, String userId) {
        log.info("Creating event: {} by user: {}", event.getName(), userId);
        event.setCreatedBy(userId);
        Event saved = repository.save(event);
        log.info("Event created with ID: {}", saved.getId());

        // Send message to TicketService
        EventCreatedMessage ticketMessage = new EventCreatedMessage(saved.getId(), saved.getTotalTickets());
        rabbitTemplate.convertAndSend(RabbitMQConfig.TICKET_EXCHANGE_NAME, RabbitMQConfig.TICKET_ROUTING_KEY, ticketMessage);
        log.info("Sent message to TicketService: eventId={}, totalTickets={}", ticketMessage.getEventId(), ticketMessage.getTotalTickets());

        // Send message to UserService
        UserEventNotification userMessage = new UserEventNotification(userId, saved.getId(), saved.getName());
        rabbitTemplate.convertAndSend(RabbitMQConfig.USER_EXCHANGE_NAME, RabbitMQConfig.USER_ROUTING_KEY, userMessage);
        log.info("Sent notification to UserService: userId={}, eventId={}, eventName={}", userMessage.getUserId(), userMessage.getEventId(), userMessage.getEventName());

        return saved;
    }
}