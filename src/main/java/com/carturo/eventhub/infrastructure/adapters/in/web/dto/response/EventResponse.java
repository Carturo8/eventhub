package com.carturo.eventhub.infrastructure.adapters.in.web.dto.response;

import com.carturo.eventhub.domain.model.event.EventCategory;
import com.carturo.eventhub.domain.model.event.EventStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;

public record EventResponse(
        Long id,
        String name,
        String description,
        EventCategory category,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        LocalDate eventDate,
        VenueResponse venue,
        EventStatus status
) {}