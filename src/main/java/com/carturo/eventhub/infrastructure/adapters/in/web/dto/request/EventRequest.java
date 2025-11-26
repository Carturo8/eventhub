package com.carturo.eventhub.infrastructure.adapters.in.web.dto.request;

import com.carturo.eventhub.domain.model.event.EventCategory;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public record EventRequest(
        @NotBlank(message = "Event name cannot be empty")
        @Size(max = 255, message = "Event name cannot exceed 255 characters")
        String name,

        @Size(max = 500, message = "Description cannot exceed 500 characters")
        String description,

        @NotNull(message = "Event category cannot be null")
        EventCategory category,

        @NotNull(message = "Event date cannot be null")
        @FutureOrPresent(message = "Event date must be today or in the future")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        LocalDate eventDate,

        @NotNull(message = "Venue ID cannot be null")
        Long venueId
) {}