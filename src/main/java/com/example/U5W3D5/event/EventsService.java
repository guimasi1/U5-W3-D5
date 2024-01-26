package com.example.U5W3D5.event;

import com.example.U5W3D5.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class EventsService {
    @Autowired
    EventsDAO eventsDAO;

    public Event save(NewEventDTO body) {
        Event event = new Event();
        event.setTitle(body.title());
        event.setDescription(body.description());
        event.setDate(LocalDate.parse(body.date()));
        event.setLocation(body.location());
        event.setMaxParticipants(body.maxParticipants());
        return eventsDAO.save(event);
    }


    public Event findById(UUID id) {
        return eventsDAO.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public Page<Event> findAll(int page, int size, String orderBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return eventsDAO.findAll(pageable);
    }


}
