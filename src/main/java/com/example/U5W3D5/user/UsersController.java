package com.example.U5W3D5.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/users")
public class UsersController {
    @Autowired
    private UsersService usersService;
}
