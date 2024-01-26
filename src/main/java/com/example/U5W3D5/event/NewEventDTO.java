package com.example.U5W3D5.event;

import jakarta.validation.constraints.*;


public record NewEventDTO(
        @NotEmpty
        String title,
        @NotEmpty
        String description,
        @NotEmpty
        String date,
        @NotEmpty
        String location,
        @Min(value = 3, message = "I partecipanti devono essere almeno 3")
        @Max(value = 10000, message = "I partecipanti possono essere massimo 10000")
        int maxParticipants
) {
}
