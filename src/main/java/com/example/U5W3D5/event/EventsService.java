package com.example.U5W3D5.event;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.U5W3D5.exceptions.NotFoundException;
import com.example.U5W3D5.exceptions.ParticipationException;
import com.example.U5W3D5.user.User;
import com.example.U5W3D5.user.UsersDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class EventsService {
    @Autowired
    EventsDAO eventsDAO;

    @Autowired
    Cloudinary cloudinary;

    @Autowired
    UsersDAO usersDAO;
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
    public Event updateById(UUID id, NewEventDTO body) {
        Event event = eventsDAO.findById(id).orElseThrow(() -> new NotFoundException(id));
        event.setTitle(body.title());
        event.setDescription(body.description());
        event.setDate(LocalDate.parse(body.date()));
        event.setLocation(body.location());
        event.setMaxParticipants(body.maxParticipants());
        return eventsDAO.save(event);
    }

    public void deleteById(UUID id) {
        Event event = eventsDAO.findById(id).orElseThrow(() -> new NotFoundException(id));
        eventsDAO.delete(event);
    }

    public Event uploadImage(MultipartFile file, UUID id) throws IOException {

        String url = (String) cloudinary.uploader()
                .upload(file.getBytes(), ObjectUtils.emptyMap())
                .get("url");
        Event event = this.findById(id);

        event.setImageUrl(url);
        return eventsDAO.save(event);
    }




}
