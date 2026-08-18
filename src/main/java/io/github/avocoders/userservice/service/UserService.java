package io.github.avocoders.userservice.service;

import io.github.avocoders.userservice.dtos.UserDto;

import java.util.List;
import java.util.Optional;

public interface UserService {
    UserDto createUser(String name, String email, Integer age);
    Optional<UserDto> getUserById(Long id);
    List<UserDto> getAllUsers();
    Optional<UserDto> updateUser(
            Long id,
            String name,
            String email,
            Integer age
    );
    boolean deleteUser(Long id);
}
