package io.github.avocoders.userservice.service;

import io.github.avocoders.userservice.dtos.UserDto;
import io.github.avocoders.userservice.entity.User;
import io.github.avocoders.userservice.mappers.UserMapper;
import io.github.avocoders.userservice.repository.UserRepository;
import io.github.avocoders.userservice.validators.UserValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;
    @Mock
    private UserValidator userValidator;
    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void createUser_shouldReturnDto_whenInputIsValid() {

        String name = "Veronika";
        String email = "veronika@ya.ru";
        Integer age = 32;
        User savedUser = new User(name, email, age);
        UserDto expectedDto = new UserDto(1L, name, email);
        when(userRepository.save(any(User.class))).thenReturn(savedUser);
        when(userMapper.toDto(savedUser)).thenReturn(expectedDto);

        UserDto actualDto = userService.createUser(name, email, age);

        assertSame(expectedDto, actualDto);

        verify(userValidator).validateName(name);
        verify(userValidator).validateEmail(email);
        verify(userValidator).validateAge(age);

        ArgumentCaptor<User> argumentCaptor = ArgumentCaptor.forClass(User.class);

        verify(userRepository).save(argumentCaptor.capture());

        User capturedUser = argumentCaptor.getValue();

        assertEquals(name, capturedUser.getName());
        assertEquals(email, capturedUser.getEmail());
        assertEquals(age, capturedUser.getAge());

        verify(userMapper).toDto(savedUser);
    }


}