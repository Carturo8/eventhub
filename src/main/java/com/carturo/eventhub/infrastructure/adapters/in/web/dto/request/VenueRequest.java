package com.carturo.eventhub.infrastructure.adapters.in.web.dto.request;

import jakarta.validation.constraints.*;

public record VenueRequest(
        @NotBlank(message = "Venue name cannot be empty")
        @Size(max = 255, message = "Venue name cannot exceed 255 characters")
        String name,

        @NotBlank(message = "Venue city cannot be empty")
        @Size(max = 255, message = "City cannot exceed 255 characters")
        String city,

        @NotNull(message = "Venue capacity cannot be null")
        @Min(value = 1, message = "Capacity must be at least 1")
        Integer capacity
) {}