package com.example.U5W3D5.user;


import com.example.U5W3D5.event.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequestMapping("/users")
public class UsersController {
    @Autowired
    private UsersService usersService;

    @GetMapping
    public Page<User> getUsers(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String surname,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String id) {
        return usersService.getUsers(name, surname, page, size, id);
    }

    // metodo per promuovere ad organizzatore
    @GetMapping("/{id}/admin")
    @PreAuthorize("hasAuthority('ADMIN')")
    public User setAdmin(@PathVariable UUID id) {
        return usersService.promoteToAdmin(id);
    }

    // metodo per rimuovere ruolo organizzatore
    @GetMapping("/{id}/demote")
    @PreAuthorize("hasAuthority('ADMIN')")
    public User demote(@PathVariable UUID id) {
        return usersService.demoteToUser(id);
    }


    @GetMapping("/me")
    public User getMyProfile(@AuthenticationPrincipal User myUser) {
        return myUser;
    }

    // metodo per prenotare un evento in base all'id dell'evento
    @GetMapping("/me/book/{id}")
    public Event bookEvent(@AuthenticationPrincipal User user, @PathVariable UUID id) {
        return usersService.bookEvent(user, id);
    }

    // metodo per vedere gli eventi in base all'utente

    @GetMapping("/me/events")
    public Page<Event> getEvents(@AuthenticationPrincipal User user,
                                 @RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "10") int size,
                                 @RequestParam(defaultValue = "id") String orderBy) {
        return usersService.getEvents(user, page, size, orderBy);
    }

    @GetMapping("/me/unbook/{id}")
    public User removeBooking(@AuthenticationPrincipal User user, @PathVariable UUID id) {
        return usersService.removeBooking(user,id);
    }


}
