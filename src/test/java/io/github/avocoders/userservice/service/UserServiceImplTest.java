package io.github.avocoders.userservice.service;

import io.github.avocoders.userservice.dtos.UserDto;
import io.github.avocoders.userservice.entity.User;
import io.github.avocoders.userservice.mappers.UserMapper;
import io.github.avocoders.userservice.repository.UserRepository;
import io.github.avocoders.userservice.validators.UserValidator;
import org.checkerframework.checker.index.qual.LengthOf;
import org.hibernate.Length;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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

    @Test
    void getUserById_shouldReturnDto_whenUserExists() {
        Long id = 1L;
        User foundUser = new User("Anna", "anna@ya.ru", 3);
        UserDto expectedDto = new UserDto(id, "Anna", "anna@ya.ru");

        when(userRepository.findById(id)).thenReturn(Optional.of(foundUser));

        when(userMapper.toDto(foundUser)).thenReturn(expectedDto);

        Optional<UserDto> actual = userService.getUserById(id);

        assertTrue(actual.isPresent());
        assertSame(expectedDto, actual.get());

        verify(userValidator).validateId(id);
        verify(userRepository).findById(id);
        verify(userMapper).toDto(foundUser);
    }

    @Test
    void getUserById_shouldReturnEmpty_whenUserDoesNotExist(){
        Long id = 1L;

        when(userRepository.findById(id)).thenReturn(Optional.empty());

        Optional<UserDto> actual = userService.getUserById(id);
        assertTrue(actual.isEmpty());

        verify(userValidator).validateId(id);
        verify(userRepository).findById(id);
        verifyNoInteractions(userMapper);
    }

    @Test
    void getAllUsers_shouldReturnDtos_whenUsersExist(){
        User user1 = new User("Sasha", "sasha@ya.ru", 5);
        User user2 = new User("Kirill", "kirill@ya.ru", 5);
        UserDto expectedDto1 = new UserDto(2L, "Sasha", "sasha@ya.ru");
        UserDto expectedDto2 = new UserDto(3L, "Kirill", "kirill@ya.ru");

        when(userRepository.findAll()).thenReturn(List.of(user1, user2));
        when(userMapper.toDto(user1)).thenReturn(expectedDto1);
        when(userMapper.toDto(user2)).thenReturn(expectedDto2);

        List<UserDto> actual = userService.getAllUsers();

        assertEquals(2, actual.size());
        assertSame(expectedDto1, actual.get(0));
        assertSame(expectedDto2, actual.get(1));

        verify(userRepository).findAll();
        verify(userMapper).toDto(user1);
        verify(userMapper).toDto(user2);
        verifyNoInteractions(userValidator);
    }
}