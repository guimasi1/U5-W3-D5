package com.example.U5W3D5.event;

import com.example.U5W3D5.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/events")
public class EventsController {
    @Autowired
    private EventsService eventService;

    @GetMapping
    public Page<Event> getEvents(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "10") int size,
                                 @RequestParam(defaultValue = "id") String sort) {
        return eventService.findAll(page, size, sort);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public EventsResponseDTO createEvent(@RequestBody @Validated NewEventDTO body, BindingResult validation) {
        if (validation.hasErrors()) {
            System.out.println(validation.getAllErrors());
            throw new BadRequestException("Ci sono errori nel payload!");
        } else {
           Event event = eventService.save(body);
           return new EventsResponseDTO(event.getId());
        }
    }



}
