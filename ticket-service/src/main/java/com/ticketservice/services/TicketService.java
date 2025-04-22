package com.ticketservice.services;

import com.ticketservice.models.Ticket;
import com.ticketservice.repos.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TicketService {

    @Autowired private TicketRepository ticketRepository;

    public void bookTicket(Ticket ticket) {
        ticket.setBookingTime(LocalDateTime.now());
        ticketRepository.save(ticket);
    }

    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    public Ticket getTicket(int id) {
        return ticketRepository.findById(id).orElse(null);
    }

    public void cancelTicket(int id) {
        ticketRepository.deleteById(id);
    }

    public List<Ticket> getTicketsByUserId(String userId) {
        return ticketRepository.findByUserid(userId);
    }

    public List<Ticket> getTicketsByEventId(String eventId) {
        return ticketRepository.findByEventid(eventId);
    }

}
