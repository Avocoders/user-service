package io.github.avocoders.userservice.handler;

import io.github.avocoders.userservice.exception.UserPersistenceException;
import io.github.avocoders.userservice.exception.UserValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserExceptionHandler.class);

    public  void handle(UserValidationException exception){
        LOGGER.warn("Validation failed: {}", exception.getMessage());
       System.out.println("Validation error: " + exception.getMessage());
    }
    public void handle(UserPersistenceException exception){
        LOGGER.error("Database operation failed", exception);
        System.out.println("Database error: " + exception.getMessage());
    }
    public void handle(NumberFormatException exception){
        LOGGER.warn("Input error number");
        System.out.println("Input error: enter a whole number");
    }
}
