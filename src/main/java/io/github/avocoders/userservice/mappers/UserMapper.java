package io.github.avocoders.userservice.mappers;

import io.github.avocoders.userservice.dtos.UserDto;
import io.github.avocoders.userservice.entity.User;

public class UserMapper {
    public UserDto toDto(User user){
        return new UserDto(user.getId(), user.getName(), user.getEmail());
    }
}
