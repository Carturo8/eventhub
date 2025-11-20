package com.carturo.eventhub.mapper;

import com.carturo.eventhub.dto.request.VenueRequest;
import com.carturo.eventhub.dto.response.VenueResponse;
import com.carturo.eventhub.entity.VenueEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(
        componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
public interface VenueMapper {

    @Mapping(target = "id", ignore = true)
    VenueEntity toEntity(VenueRequest request);

    VenueResponse toResponse(VenueEntity entity);
}