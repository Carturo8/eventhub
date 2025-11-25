package com.carturo.eventhub.infrastructure.adapters.out.jpa.mapper;

import com.carturo.eventhub.domain.model.Venue;
import com.carturo.eventhub.infrastructure.adapters.out.jpa.entity.VenueEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VenueEntityMapper {

    Venue toDomain(VenueEntity entity);

    VenueEntity toEntity(Venue domain);
}