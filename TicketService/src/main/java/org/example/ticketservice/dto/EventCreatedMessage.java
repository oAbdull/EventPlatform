// TicketService/src/main/java/org/example/ticketservice/dto/EventCreatedMessage.java
package org.example.ticketservice.dto;

public class EventCreatedMessage {
    private String eventId;
    private int totalTickets;

    // Default constructor for deserialization
    public EventCreatedMessage() {}

    public EventCreatedMessage(String eventId, int totalTickets) {
        this.eventId = eventId;
        this.totalTickets = totalTickets;
    }

    // Getters and setters
    public String getEventId() { return eventId; }
    public void setEventId(String eventId) { this.eventId = eventId; }
    public int getTotalTickets() { return totalTickets; }
    public void setTotalTickets(int totalTickets) { this.totalTickets = totalTickets; }
}