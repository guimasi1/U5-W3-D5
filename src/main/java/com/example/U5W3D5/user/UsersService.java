package com.example.U5W3D5.user;


import com.example.U5W3D5.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class UsersService {
    @Autowired
    UsersDAO usersDAO;

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


}
