package org.example.ticketservice.repo;

import org.example.ticketservice.model.Ticket;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TicketRepository extends MongoRepository<Ticket, String> {
    List<Ticket> findByEventId(String eventId);
}