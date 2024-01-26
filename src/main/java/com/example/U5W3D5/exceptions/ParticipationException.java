package com.example.U5W3D5.exceptions;

import com.example.U5W3D5.event.Event;
import com.example.U5W3D5.user.User;

public class ParticipationException extends RuntimeException {

    public ParticipationException(String message) {
        super(message);
    }

    public ParticipationException(User user, Event event) {
        super("L'utente " + user.getUsername() + " è già prenotato per l'evento " + event.getTitle());
    }

}
