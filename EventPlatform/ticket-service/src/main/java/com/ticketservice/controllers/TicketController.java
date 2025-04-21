package com.ticketservice.controllers;

import com.ticketservice.models.Ticket;
import com.ticketservice.services.PublisherService;
import com.ticketservice.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;
    @Autowired
    private PublisherService publisherService;

    // Endpoint to book a ticket
    @PostMapping
    public ResponseEntity<?> bookTicket(@RequestBody Ticket ticket) {
        try {
            ticketService.bookTicket(ticket);
            //publisherService.publishMessage("Ticket booked successfully: " + ticket.getId());
            return ResponseEntity.ok("Ticket booked successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error booking ticket: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllTickets() {
        try {
            return ResponseEntity.ok(ticketService.getAllTickets());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error fetching tickets: " + e.getMessage());
        }
    }

    // Endpoint to get ticket details
    @GetMapping("/{id}")
    public ResponseEntity<?> getTicket(@PathVariable int id) {
        try {
            Ticket ticket = ticketService.getTicket(id);
            if (ticket != null) {
                return ResponseEntity.ok(ticket);
            } else {
                return ResponseEntity.status(404).body("Ticket not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error fetching ticket: " + e.getMessage());
        }
    }

    // Endpoint to cancel a ticket
    @DeleteMapping("/{id}")
    public ResponseEntity<?> cancelTicket(@PathVariable int id) {
        try {
            ticketService.cancelTicket(id);
            return ResponseEntity.ok("Ticket cancelled successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error cancelling ticket: " + e.getMessage());
        }
    }

    // Endpoint to get tickets by user ID
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getTicketsByUserId(@PathVariable String userId) {
        try {
            return ResponseEntity.ok(ticketService.getTicketsByUserId(userId));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error fetching tickets: " + e.getMessage());
        }
    }

    // Endpoint to get tickets by event ID
    @GetMapping("/event/{eventId}")
    public ResponseEntity<?> getTicketsByEventId(@PathVariable String eventId) {
        try {
            return ResponseEntity.ok(ticketService.getTicketsByEventId(eventId));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error fetching tickets: " + e.getMessage());
        }
    }

}
