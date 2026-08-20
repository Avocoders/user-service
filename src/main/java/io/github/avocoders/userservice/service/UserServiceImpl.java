package io.github.avocoders.userservice.service;

import io.github.avocoders.userservice.dtos.UserDto;
import io.github.avocoders.userservice.entity.User;
import io.github.avocoders.userservice.mappers.UserMapper;
import io.github.avocoders.userservice.repository.UserRepository;
import io.github.avocoders.userservice.validators.UserValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService{
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

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
        LOGGER.info("User created successfully, id={}", savedUser.getId());
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
        userValidator.validateId(id);
        userValidator.validateName(name);
        userValidator.validateEmail(email);
        userValidator.validateAge(age);
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            return Optional.empty();
        }
        User user = optionalUser.get();
        user.setName(name);
        user.setEmail(email);
        user.setAge(age);
        User updatedUser = userRepository.update(user);
        UserDto userDto = userMapper.toDto(updatedUser);
        LOGGER.info("User updated successfully, id={}", userDto.getId());
        return Optional.of(userDto);
    }

    @Override
    public boolean deleteUser(Long id) {
        userValidator.validateId(id);
        boolean deleted = userRepository.deleteById(id);
        if (deleted) {
            LOGGER.info("User deleted successfully, id={}", id);
        }
        return deleted;
    }
}
