package org.example.eventservice.controller;

import org.example.eventservice.model.Event;
import org.example.eventservice.repo.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {

    @Autowired
    private EventRepository eventRepository;

    @GetMapping
    public List<Event> getEventsByUser(@RequestParam String user) {
        return eventRepository.findAll().stream()
                .filter(event -> event.getUser().equals(user))
                .toList();
    }
    @GetMapping("/all")
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }
}