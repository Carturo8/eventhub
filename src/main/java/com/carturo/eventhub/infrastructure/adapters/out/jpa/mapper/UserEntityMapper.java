package com.carturo.eventhub.infrastructure.adapters.out.jpa.mapper;

import com.carturo.eventhub.domain.model.user.User;
import com.carturo.eventhub.infrastructure.adapters.out.jpa.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserEntityMapper {

    User toDomain(UserEntity userEntity);

    UserEntity toEntity(User user);
}