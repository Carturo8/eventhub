package com.carturo.eventhub.infrastructure.adapters.in.web.mapper;

import com.carturo.eventhub.domain.model.user.User;
import com.carturo.eventhub.infrastructure.adapters.in.web.dto.request.RegisterRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AuthWebMapper {

    @Mapping(target = "id", ignore = true)
    User toDomain(RegisterRequest registerRequest);
}