package org.example.ticketservice.service;

import org.example.ticketservice.dto.BookingRequest;
import org.example.ticketservice.model.Ticket;
import org.example.ticketservice.repo.TicketRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService implements ITicketService {

    private static final Logger log = LoggerFactory.getLogger(TicketService.class);

    private final TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Override
    public Ticket bookTickets(String eventId, BookingRequest request) {
        log.info("Booking {} tickets for eventId: {} by user: {}", request.getNumberOfTickets(), eventId, request.getUserId());
        List<Ticket> tickets = ticketRepository.findByEventId(eventId);
        if (tickets.isEmpty()) {
            throw new RuntimeException("No tickets available for eventId: " + eventId);
        }
        Ticket ticket = tickets.get(0); // Assuming one ticket entry per event
        if (ticket.getAvailableTickets() < request.getNumberOfTickets()) {
            throw new RuntimeException("Not enough tickets available for eventId: " + eventId);
        }

        ticket.setAvailableTickets(ticket.getAvailableTickets() - request.getNumberOfTickets());
        ticket.setBookedTickets(ticket.getBookedTickets() + request.getNumberOfTickets());
        ticket.setBookedBy(request.getUserId());
        ticketRepository.save(ticket);
        log.info("Booked {} tickets for eventId: {}. Remaining tickets: {}", request.getNumberOfTickets(), eventId, ticket.getAvailableTickets());
        return ticket;
    }

    @Override
    public List<Ticket> getTicketsByEventId(String eventId) {
        log.info("Retrieving tickets for eventId: {}", eventId);
        return ticketRepository.findByEventId(eventId);
    }

    @Override
    public int getAvailableTickets(String eventId) {
        log.info("Retrieving available tickets for eventId: {}", eventId);
        List<Ticket> tickets = ticketRepository.findByEventId(eventId);
        if (tickets.isEmpty()) {
            return 0;
        }
        return tickets.get(0).getAvailableTickets();
    }
}