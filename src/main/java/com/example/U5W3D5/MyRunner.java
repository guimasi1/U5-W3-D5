package com.example.U5W3D5;

import com.example.U5W3D5.user.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class MyRunner implements CommandLineRunner {

    @Autowired
    UsersService usersService;
    @Override
    public void run(String... args) throws Exception {
        System.out.println("Hello");
        // usersService.findAll().forEach(System.out::println);
    }



}