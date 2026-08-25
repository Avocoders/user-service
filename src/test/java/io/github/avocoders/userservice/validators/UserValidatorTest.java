package io.github.avocoders.userservice.validators;

import io.github.avocoders.userservice.exception.UserValidationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserValidatorTest {

    private final UserValidator userValidator = new UserValidator();

    @Test
    void validateId_whenIdIsValid() {
        Long id = 50L;

        assertDoesNotThrow(() -> userValidator.validateId(id));
    }

    @Test
    void validateId_shouldThrowException_whenIdIsNegative() {
        Long id = -5L;

        assertThrows( UserValidationException.class, () -> userValidator.validateId(id));
    }

    @Test
    void validateId_shouldThrowException_whenIdIsZero() {
        Long id = 0L;

        assertThrows( UserValidationException.class, () -> userValidator.validateId(id));
    }

    @Test
    void validateId_shouldThrowException_whenIdIsNull() {
        Long id = null;
        assertThrows( UserValidationException.class, () -> userValidator.validateId(id));
    }

    @Test
    void validateName_whenNameIsValid() {
        String name = "Veronika";
        assertDoesNotThrow(() -> userValidator.validateName(name));
    }

    @Test
    void validateName_shouldThrowException_whenNameIsNull() {
        String name = null;
        assertThrows( UserValidationException.class, () -> userValidator.validateName(name));
    }

    @Test
    void validateName_shouldThrowException_whenNameIsBlank() {
        String name = " ";
        assertThrows( UserValidationException.class, () -> userValidator.validateName(name));
    }

    @Test
    void validateName_shouldThrowException_whenNameLengthMore100() {
        String name = "f".repeat(101);
        assertThrows(UserValidationException.class, () -> userValidator.validateName(name));
    }

    @Test
    void validateEmail_whenEmailIsValid() {
        String email = "aleksey@ya.com";
        assertDoesNotThrow( () -> userValidator.validateEmail(email));
    }

    @Test
    void validateEmail_shouldThrowException_whenEmailIsNull() {
        String email = null;
        assertThrows( UserValidationException.class, () -> userValidator.validateEmail(email));
    }

    @Test
    void validateEmail_shouldThrowException_whenEmailIsBlank() {
        String email = "  ";
        assertThrows( UserValidationException.class, () -> userValidator.validateEmail(email));
    }

    @Test
    void validateEmail_shouldThrowException_whenEmailLengthMore255() {
        String email = "f".repeat(257) + "@ya.ru";
        assertThrows( UserValidationException.class, () -> userValidator.validateEmail(email));
    }

    @Test
    void validateEmail_shouldThrowException_whenEmailIsInvalid() {
        String email = "k.jnfgiusd@dfbd";
        assertThrows( UserValidationException.class, () -> userValidator.validateEmail(email));
    }

}