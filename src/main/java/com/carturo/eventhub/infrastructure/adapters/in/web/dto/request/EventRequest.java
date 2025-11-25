package com.carturo.eventhub.infrastructure.adapters.in.web.dto.request;

import jakarta.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;

public record EventRequest(
        @NotBlank(message = "Event name cannot be empty")
        @Size(max = 255, message = "Event name cannot exceed 255 characters")
        String name,

        @Size(max = 500, message = "Description cannot exceed 500 characters")
        String description,

        @Size(max = 100, message = "Category cannot exceed 100 characters")
        String category,

        @NotNull(message = "Event date cannot be null")
        @FutureOrPresent(message = "Event date must be today or in the future")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        LocalDate date,

        @NotNull(message = "Venue ID cannot be null")
        Long venueId
) {}