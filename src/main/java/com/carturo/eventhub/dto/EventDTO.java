package com.carturo.eventhub.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EventDTO {
    private Long id;

    @NotBlank(message = "Event name cannot be empty")
    private String name;

    private String description;

    @NotBlank(message = "Event date cannot be empty")
    private String date;

    @NotNull(message = "Venue ID cannot be null")
    private Long venueId;
}