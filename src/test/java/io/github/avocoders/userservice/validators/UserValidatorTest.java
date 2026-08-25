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
}