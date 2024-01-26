package com.example.U5W3D5.user;


import com.example.U5W3D5.event.Event;
import com.example.U5W3D5.event.EventsDAO;
import com.example.U5W3D5.exceptions.NotFoundException;
import com.example.U5W3D5.exceptions.ParticipationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
public class UsersService {
    @Autowired
    UsersDAO usersDAO;

    @Autowired
    EventsDAO eventsDAO;

    public List<User> findAll() {
        return usersDAO.findAll();
    }

    public Page<User> getUsers(int page, int size, String orderBy) {
        Pageable pageable = PageRequest.of(page,size, Sort.by(orderBy));
        return usersDAO.findAll(pageable);
    }
    public User findById(UUID id) {
        return usersDAO.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public User findByIdAndUpdate(UUID uuid, User body) {
        User found = this.findById(uuid);
        found.setName(body.getName());
        found.setSurname(body.getSurname());
        found.setUsername(body.getUsername());
        found.setEmail(body.getEmail());
        return usersDAO.save(found);
    }

    public void deleteById(UUID uuid) {
        User found = this.findById(uuid);
        usersDAO.delete(found);
    }
    public User findByEmail(String email) throws NotFoundException {
        System.out.println(usersDAO.findByEmail(email));
        return usersDAO.findByEmail(email).orElseThrow(() -> new NotFoundException("Utente con email " + email + " non trovata!"));
    }

    public Page<User> getUsers(String name, String surname, int page, int size, String orderBy) {
        Pageable pageable = PageRequest.of(page,size, Sort.by(orderBy));
        if(name == null && surname == null) return usersDAO.findAll(pageable);
        if(name == null) return usersDAO.findBySurname(surname,pageable);
        if(surname == null) return usersDAO.findByName(name,pageable);
        return usersDAO.findByNameAndSurname(name,surname,pageable);

    }

    public User promoteToAdmin(UUID id) {
        User user = usersDAO.findById(id).orElseThrow(() -> new NotFoundException(id));
        user.setRole(UserRole.ADMIN);
        return usersDAO.save(user);
    }

    public Event bookEvent(User currentUser, UUID eventId) {
        User user = this.findById(currentUser.getId());
        Event event = eventsDAO.findById(eventId).orElseThrow(() -> new NotFoundException(eventId));
        if (event.getUsers().contains(user)) throw new ParticipationException(user, event);
        if (event.getUsers().size() < event.getMaxParticipants()) {
            event.getUsers().add(user);
            return eventsDAO.save(event);
        } else {
            throw new ParticipationException("Non ci sono più posti disponibili");
        }
    }

    public Page<Event> getEvents(User user, int page, int size, String orderBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return eventsDAO.findByUsers(user, pageable);
    }

    public User removeBooking(User currentUser, UUID eventId) {
        User user = this.findById(currentUser.getId());
        Event event = eventsDAO.findById(eventId).orElseThrow(() -> new NotFoundException(eventId));
        if(!user.getEvents().contains(event)) throw new ParticipationException("Spiacenti, non risulta prenotato per questo evento, non è pertanto possibile cancellare la prenotazione.");
        user.getEvents().remove(event);
        event.getUsers().remove(user);

        eventsDAO.save(event);
        return usersDAO.save(user);
    }



}
