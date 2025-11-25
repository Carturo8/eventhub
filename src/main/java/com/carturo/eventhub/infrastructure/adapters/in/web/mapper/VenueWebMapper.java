package com.carturo.eventhub.infrastructure.adapters.in.web.mapper;

import com.carturo.eventhub.domain.model.Venue;
import com.carturo.eventhub.infrastructure.adapters.in.web.dto.request.VenueRequest;
import com.carturo.eventhub.infrastructure.adapters.in.web.dto.response.VenueResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VenueWebMapper {

    @Mapping(target = "id", ignore = true)
    Venue toDomain(VenueRequest request);

    VenueResponse toResponse(Venue domain);
}