package com.carturo.eventhub.infrastructure.adapters.out.jpa.mapper;

import com.carturo.eventhub.domain.model.event.Event;
import com.carturo.eventhub.infrastructure.adapters.out.jpa.entity.EventEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring",
        uses = { VenueEntityMapper.class }
)
public interface EventEntityMapper {

    @Mapping(target = "venue", source = "venue")
    Event toDomain(EventEntity entity);

    @Mapping(target = "venue", source = "venue")
    EventEntity toEntity(Event domain);
}