// TicketService/src/main/java/org/example/ticketservice/model/Ticket.java
package org.example.ticketservice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "tickets")
public class Ticket {
    @Id
    private String id;
    private String eventId;
    private int availableTickets;
    private int bookedTickets;
    private String bookedBy; // User ID of the user who booked the tickets

    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getEventId() { return eventId; }
    public void setEventId(String eventId) { this.eventId = eventId; }
    public int getAvailableTickets() { return availableTickets; }
    public void setAvailableTickets(int availableTickets) { this.availableTickets = availableTickets; }
    public int getBookedTickets() { return bookedTickets; }
    public void setBookedTickets(int bookedTickets) { this.bookedTickets = bookedTickets; }
    public String getBookedBy() { return bookedBy; }
    public void setBookedBy(String bookedBy) { this.bookedBy = bookedBy; }
}