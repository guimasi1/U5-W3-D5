package com.example.U5W3D5.authentication;

import com.example.U5W3D5.config.MailgunSender;
import com.example.U5W3D5.exceptions.BadRequestException;
import com.example.U5W3D5.user.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    AuthService authService;

    @Autowired
    UsersService usersService;

    @Autowired
    MailgunSender mailgunSender;
    @PostMapping("/login")
    public UserLoginResponseDTO login(@RequestBody UserLoginDTO body) {
        String token = authService.authenticateUser(body);
        return new UserLoginResponseDTO(token);
    }


    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UsersResponseDTO createUser(@RequestBody @Validated NewUserDTO newUserPayload, BindingResult validation, @Value("${mail.from}") String mailFrom) {
        System.out.println(validation);
        if (validation.hasErrors()) {
            System.out.println(validation.getAllErrors());
            throw new BadRequestException("Ci sono errori nel payload!");
        } else {
            User newUser = authService.save(newUserPayload);
            // mailgunSender.sendMail(newUser.getEmail(),mailFrom);
            return new UsersResponseDTO(newUser.getId());
        }
    }

}
