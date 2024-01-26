package com.example.U5W3D5.event;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;


public record NewEventDTO(
        @NotEmpty
        @Size(min = 3,max = 20, message = "Il titolo deve essere di minimo 3 caratteri e massimo 20")
        String title,
        @NotEmpty
        @Size(min = 5,max = 20, message = "La descrizione deve essere di minimo 5 caratteri e massimo 20")
        String description,
        String date,
        @NotEmpty
        @Size(min = 5,max = 20, message = "La location deve essere di minimo 5 caratteri e massimo 20")
        String location,
        @Min(value = 3, message = "I partecipanti devono essere almeno 3")
        @Max(value = 10000, message = "I partecipanti possono essere massimo 10000")
        int maxParticipants
) {
}
