package com.tms.api.users.mapper;

import com.tms.api.users.dto.UserDto;
import com.tms.api.users.entity.User;
import com.tms.api.users.model.user.CreateUserRequestModel;
import com.tms.api.users.model.user.UpdateUserRequestModel;
import com.tms.api.users.model.user.UserResponseModel;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public interface UserMapper {

    User dtoToEntity(UserDto dto);

    UserDto entityToDto(User entity);

    UserDto createRequestToDto(CreateUserRequestModel createUserRequestModel);

    UserDto updateRequestToDto(UpdateUserRequestModel updateUserRequestModel);

    UserResponseModel createResponseFromDto(UserDto dto);


    default List createFromEntities(final Collection<User> entities) {
        return entities.stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }

    default List createFromDtos(final Collection<UserDto> dtos) {
        return dtos.stream()
                .map(this::dtoToEntity)
                .collect(Collectors.toList());
    }
}