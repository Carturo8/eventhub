package com.carturo.eventhub.mapper;

import com.carturo.eventhub.dto.request.EventRequest;
import com.carturo.eventhub.dto.response.EventResponse;
import com.carturo.eventhub.entity.EventEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(
        componentModel = "spring",
        uses = { VenueMapper.class },
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
public interface EventMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "venue", ignore = true) // se asigna en Service
    EventEntity toEntity(EventRequest request);

    @Mapping(target = "venue", source = "venue")
    EventResponse toResponse(EventEntity entity);
}