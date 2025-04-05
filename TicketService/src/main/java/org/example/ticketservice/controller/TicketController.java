package org.example.ticketservice.controller;

import org.example.ticketservice.dto.BookingRequest;
import org.example.ticketservice.model.Ticket;
import org.example.ticketservice.service.ITicketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private static final Logger log = LoggerFactory.getLogger(TicketController.class);

    private final ITicketService ticketService;

    public TicketController(ITicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping("/{eventId}/book")
    public ResponseEntity<Ticket> bookTickets(@PathVariable String eventId, @RequestBody BookingRequest request) {
        log.info("Received request to book tickets for eventId: {} by user: {}", eventId, request.getUserId());
        try {
            Ticket ticket = ticketService.bookTickets(eventId, request);
            return ResponseEntity.ok(ticket);
        } catch (RuntimeException e) {
            log.error("Failed to book tickets: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<List<Ticket>> getTicketsByEventId(@PathVariable String eventId) {
        log.info("Received request to get tickets for eventId: {}", eventId);
        List<Ticket> tickets = ticketService.getTicketsByEventId(eventId);
        return ResponseEntity.ok(tickets);
    }

    @GetMapping("/{eventId}/available")
    public ResponseEntity<Integer> getAvailableTickets(@PathVariable String eventId) {
        log.info("Received request to get available tickets for eventId: {}", eventId);
        int availableTickets = ticketService.getAvailableTickets(eventId);
        return ResponseEntity.ok(availableTickets);
    }
}