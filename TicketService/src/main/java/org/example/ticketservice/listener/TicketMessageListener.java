package org.example.ticketservice.listener;

import org.example.ticketservice.dto.EventCreatedMessage;
import org.example.ticketservice.model.Ticket;
import org.example.ticketservice.repo.TicketRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class TicketMessageListener {

    private static final Logger log = LoggerFactory.getLogger(TicketMessageListener.class);

    private final TicketRepository ticketRepository;

    public TicketMessageListener(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @RabbitListener(queues = "event.created.queue")
    public void handleEventCreated(EventCreatedMessage message) {
        log.info("Received message: eventId={}, totalTickets={}", message.getEventId(), message.getTotalTickets());

        // Create a ticket entry for the event
        Ticket ticket = new Ticket();
        ticket.setEventId(message.getEventId());
        ticket.setAvailableTickets(message.getTotalTickets());
        ticket.setBookedTickets(0);
        ticket.setBookedBy(null);
        ticketRepository.save(ticket);
        log.info("Created ticket entry for eventId={} with {} available tickets", message.getEventId(), message.getTotalTickets());
    }
}