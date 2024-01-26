package com.example.U5W3D5.event;

import jakarta.validation.constraints.*;


public record NewEventDTO(
        @NotEmpty
        @Size(min = 3, max = 30, message = "Il titolo deve avere minimo 3 caratteri e massimo 30")
        String title,
        @NotEmpty
        @Size(min = 3, max = 30, message = "La descrizione deve avere minimo 3 caratteri e massimo 30")
        String description,
        @NotBlank
        @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "Il formato della data deve essere yyyy-mm-dd")
        String date,
        @NotEmpty
        @Size(min = 3, max = 30, message = "La location deve avere minimo 3 caratteri e massimo 30")
        String location,
        @Min(value = 3, message = "I partecipanti devono essere almeno 3")
        @Max(value = 10000, message = "I partecipanti possono essere massimo 10000")
        int maxParticipants
) {
}
