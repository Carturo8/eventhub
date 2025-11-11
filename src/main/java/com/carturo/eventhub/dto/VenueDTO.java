package com.carturo.eventhub.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class VenueDTO {
    private Long id;

    @NotBlank(message = "Venue name cannot be empty")
    private String name;

    @NotBlank(message = "Venue location cannot be empty")
    private String location;

    @NotNull(message = "Venue capacity cannot be null")
    private Integer capacity;
}