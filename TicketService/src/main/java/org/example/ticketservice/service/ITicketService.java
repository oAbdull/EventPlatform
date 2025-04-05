package org.example.ticketservice.service;

import org.example.ticketservice.dto.BookingRequest;
import org.example.ticketservice.model.Ticket;

import java.util.List;

public interface ITicketService {
    Ticket bookTickets(String eventId, BookingRequest request);
    List<Ticket> getTicketsByEventId(String eventId);
    int getAvailableTickets(String eventId);
}