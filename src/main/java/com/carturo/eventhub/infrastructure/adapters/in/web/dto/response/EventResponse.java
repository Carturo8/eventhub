package com.carturo.eventhub.infrastructure.adapters.in.web.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;

public record EventResponse(
        Long id,
        String name,
        String description,
        String category,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        LocalDate date,
        VenueResponse venue
) {}