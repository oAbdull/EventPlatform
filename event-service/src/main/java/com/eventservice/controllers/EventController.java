package com.eventservice.controllers;

import com.eventservice.models.Event;
import com.eventservice.services.EventService;
import com.eventservice.services.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/events")
public class EventController {
    @Autowired
    private EventService eventService;

    @Autowired
    private PublisherService publisherService;

    @PostMapping
    public ResponseEntity<?> saveEvent(@RequestBody Event event) {
        System.out.println("Received event: " + event);
        eventService.save(event);
        publisherService.publishMessage(event.toString()+" event created");
        return ResponseEntity.ok("Event saved successfully");
    }

    @GetMapping
    public ResponseEntity<?> getAllEvents() {
        return ResponseEntity.ok(eventService.listAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEventById(@PathVariable String id) {
        Event event = eventService.findById(id);
        if (event != null) {
            return ResponseEntity.ok(event);
        } else {
            return ResponseEntity.status(404).body("Event not found");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEventById(@PathVariable String id) {
        Event event = eventService.findById(id);
        if (event != null) {
            eventService.deleteById(id);
            return ResponseEntity.ok("Event deleted successfully");
        } else {
            return ResponseEntity.status(404).body("Event not found");
        }
    }
}
