package com.carturo.eventhub.dto.response;

public record VenueResponse(
        Long id,
        String name,
        String city,
        Integer capacity
) {}