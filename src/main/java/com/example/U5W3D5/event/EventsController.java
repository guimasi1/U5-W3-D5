package com.example.U5W3D5.event;

import com.example.U5W3D5.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/events")
public class EventsController {
    @Autowired
    private EventsService eventsService;

    @GetMapping
    public Page<Event> getEvents(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "10") int size,
                                 @RequestParam(defaultValue = "id") String sort) {
        return eventsService.findAll(page, size, sort);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public EventsResponseDTO createEvent(@RequestBody @Validated NewEventDTO body, BindingResult validation) {
        if (validation.hasErrors()) {
            System.out.println(validation.getAllErrors());
            throw new BadRequestException("Ci sono errori nel payload!");
        } else {
           Event event = eventsService.save(body);
           return new EventsResponseDTO(event.getId());
        }
    }
    @GetMapping("/{id}")
    public Event getEventById(@PathVariable UUID id) {
        return eventsService.findById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Event updateEventById(@PathVariable UUID id, @RequestBody NewEventDTO body, BindingResult validation) {
        if (validation.hasErrors()) {
            System.out.println(validation.getAllErrors());
            throw new BadRequestException("Ci sono errori nel payload!");
        } else {
            return eventsService.updateById(id, body);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteEventById(@PathVariable UUID id) {
        eventsService.deleteById(id);
    }


    @PatchMapping("/{id}/upload")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Event uploadImage(@RequestParam("image") MultipartFile file, @PathVariable UUID id) throws IOException {
        return eventsService.uploadImage(file, id);
    }



}
