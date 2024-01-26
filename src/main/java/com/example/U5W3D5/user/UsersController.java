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

    @GetMapping("/{id}/admin")
    @PreAuthorize("hasAuthority('ADMIN')")
    public User setAdmin(@PathVariable UUID id) {
        return usersService.promoteToAdmin(id);
    }

    @GetMapping("/me")
    public User getMyProfile(@AuthenticationPrincipal User myUser) {
        return myUser;
    }


}
