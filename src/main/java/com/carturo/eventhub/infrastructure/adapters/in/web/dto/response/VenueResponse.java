package com.carturo.eventhub.infrastructure.adapters.in.web.dto.response;

public record VenueResponse(
        Long id,
        String name,
        String city,
        Integer capacity
) {}