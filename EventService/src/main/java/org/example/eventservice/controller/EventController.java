package org.example.eventservice.controller;

import org.example.eventservice.model.Event;
import org.example.eventservice.service.IEventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private static final Logger log = LoggerFactory.getLogger(EventController.class);

    private final IEventService eventService;

    public EventController(IEventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping
    public ResponseEntity<Event> createEvent(@RequestBody Event event, @RequestParam String userId) {
        log.info("Received request to create event: {} by user: {}", event.getName(), userId);
        Event savedEvent = eventService.createEvent(event, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEvent);
    }
}