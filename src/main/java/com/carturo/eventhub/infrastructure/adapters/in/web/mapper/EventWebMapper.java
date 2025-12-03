package com.carturo.eventhub.infrastructure.adapters.in.web.mapper;

import com.carturo.eventhub.domain.model.event.Event;
import com.carturo.eventhub.infrastructure.adapters.in.web.dto.request.EventRequest;
import com.carturo.eventhub.infrastructure.adapters.in.web.dto.response.EventResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { VenueWebMapper.class })
public interface EventWebMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "venue.id", source = "venueId")
    Event toDomain(EventRequest request);

    EventResponse toResponse(Event domain);
}