package org.ticketservice.controller;

import org.ticketservice.dtos.BookingDto;
import org.ticketservice.model.Booking;
import org.ticketservice.model.Ticket;
import org.ticketservice.service.PublisherService;
import org.ticketservice.service.TicketService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;
    @Autowired
    private PublisherService publisherService;

    // Endpoint to book a ticket
    @PostMapping
    public ResponseEntity<?> bookTicket(@RequestBody BookingDto dto) {
        try {
            Ticket ticket =  new Ticket();
            BeanUtils.copyProperties(dto, ticket);
            ticketService.bookTicket(ticket);
            //send to analytics service by rabbitmq
            Booking booking = new Booking();
            booking.setBookingId(String.valueOf(ticket.getId()));
            booking.setUserId(ticket.getUserid());
            booking.setEventId(ticket.getEventid());
            booking.setPrice(dto.getPrice());
            booking.setDate(dto.getDate());
            booking.setLocation(dto.getLocation());
            booking.setBookingDate(dto.getBookingDate());
            publisherService.publishMessage(booking);
            return ResponseEntity.ok("Ticket booked successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error booking ticket: " + e.getMessage());
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

    @PostMapping("/book")
    public ResponseEntity<String> bookTicket(@RequestBody Ticket ticket) {
        ticketService.bookTicket(ticket);
        return ResponseEntity.ok("Ticket booked successfully");
    }
}