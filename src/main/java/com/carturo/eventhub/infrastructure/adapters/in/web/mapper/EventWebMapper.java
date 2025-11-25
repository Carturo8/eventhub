package com.carturo.eventhub.infrastructure.adapters.in.web.mapper;

import com.carturo.eventhub.domain.model.Event;
import com.carturo.eventhub.infrastructure.adapters.in.web.dto.request.EventRequest;
import com.carturo.eventhub.infrastructure.adapters.in.web.dto.response.EventResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { VenueWebMapper.class })
public interface EventWebMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "venue", ignore = true)
    Event toDomain(EventRequest request);

    @Mapping(target = "venue", source = "venue")
    EventResponse toResponse(Event domain);
}