package io.github.avocoders.userservice.service;

import io.github.avocoders.userservice.dtos.UserDto;
import io.github.avocoders.userservice.entity.User;
import io.github.avocoders.userservice.mappers.UserMapper;
import io.github.avocoders.userservice.repository.UserRepository;

import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper){
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserDto createUser(String name, String email, Integer age) {
        User user = new User(name, email, age);
        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }

    @Override
    public Optional<UserDto> getUserById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<UserDto> getAllUsers() {
        return List.of();
    }

    @Override
    public Optional<UserDto> updateUser(Long id, String name, String email, Integer age) {
        return Optional.empty();
    }

    @Override
    public boolean deleteUser(Long id) {
        return false;
    }
}
