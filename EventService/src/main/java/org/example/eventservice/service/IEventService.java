package org.example.eventservice.service;

import org.example.eventservice.model.Event;

public interface IEventService {
    Event createEvent(Event event, String userId);
}