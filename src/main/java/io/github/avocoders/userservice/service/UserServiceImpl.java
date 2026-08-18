package io.github.avocoders.userservice.service;

import io.github.avocoders.userservice.dtos.UserDto;
import io.github.avocoders.userservice.entity.User;
import io.github.avocoders.userservice.mappers.UserMapper;
import io.github.avocoders.userservice.repository.UserRepository;
import io.github.avocoders.userservice.validators.UserValidator;

import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserValidator userValidator;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, UserValidator userValidator){
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.userValidator = userValidator;
    }

    @Override
    public UserDto createUser(String name, String email, Integer age) {
        userValidator.validateName(name);
        userValidator.validateEmail(email);
        userValidator.validateAge(age);
        User user = new User(name, email, age);
        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }

    @Override
    public Optional<UserDto> getUserById(Long id) {
        userValidator.validateId(id);
        return userRepository.findById(id).map(userMapper::toDto);
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream().map(userMapper::toDto).toList();
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
