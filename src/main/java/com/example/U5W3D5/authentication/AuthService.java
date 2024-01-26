package com.example.U5W3D5.authentication;


import com.example.U5W3D5.exceptions.BadRequestException;
import com.example.U5W3D5.exceptions.UnauthorizedException;
import com.example.U5W3D5.security.JWTTools;
import com.example.U5W3D5.user.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private JWTTools jwtTools;
    @Autowired
    private PasswordEncoder bCrypt;

    @Autowired
    private UsersService usersService;

    @Autowired
    private UsersDAO usersDAO;




    public String authenticateUser(UserLoginDTO body) {
        User user = usersService.findByEmail(body.email());
        if(bCrypt.matches(body.password(),user.getPassword())) {
            return jwtTools.createToken(user);
        } else {
            throw new UnauthorizedException("Errore nelle credenziali");
        }
    }

    public User save(NewUserDTO user) {
        usersDAO.findByEmail(user.email()).ifPresent(user1 -> {
            throw new BadRequestException("Email " + user.email() + " già in uso");
        });
        usersDAO.findByUsername(user.username()).ifPresent(user1 -> {
            throw new BadRequestException("L'username " + user.username() + " già in uso");
        });

        User newUser = new User();
        newUser.setUsername(user.username());
        newUser.setSurname(user.surname());
        newUser.setName(user.name());
        newUser.setEmail(user.email());
        newUser.setRole(UserRole.USER);
        newUser.setPassword(bCrypt.encode(user.password()));
        return usersDAO.save(newUser);
    }
}
