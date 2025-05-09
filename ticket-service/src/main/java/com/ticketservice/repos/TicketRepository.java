package com.ticketservice.repos;

import com.ticketservice.models.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    List<Ticket> findByUserid(String userid);
    List<Ticket> findByEventid(String eventid);
}
