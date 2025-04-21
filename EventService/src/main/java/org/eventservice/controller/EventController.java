package org.eventservice.controller;


import org.eventservice.model.Event;
import org.eventservice.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/events")
public class EventController {
    @Autowired
    private EventService eventService;

    @PostMapping
    public ResponseEntity<?> saveEvent(@RequestBody Event event) {
        System.out.println("Received event: " + event);
        eventService.save(event);
        return ResponseEntity.ok("Event saved successfully");
    }

    @GetMapping
    public ResponseEntity<?> getAllEvents() {
        return ResponseEntity.ok(eventService.listAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEventById(String id) {
        Event event = eventService.findById(id);
        if (event != null) {
            return ResponseEntity.ok(event);
        } else {
            return ResponseEntity.status(404).body("Event not found");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEventById(String id) {
        Event event = eventService.findById(id);
        if (event != null) {
            eventService.deleteById(id);
            return ResponseEntity.ok("Event deleted successfully");
        } else {
            return ResponseEntity.status(404).body("Event not found");
        }
    }
}
