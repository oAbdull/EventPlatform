package org.eventservice.service;


import org.eventservice.model.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.eventservice.repo.EventRepository;

import java.util.List;

@Service
public class EventService {
    @Autowired private EventRepository repository;

    public void save(Event event){
        repository.save(event);
    }

    public List<Event> listAll(){
        return repository.findAll();
    }

    public Event findById(String id){
        return repository.findById(id).orElse(null);
    }

    public void deleteById(String id){
        repository.deleteById(id);
    }
}