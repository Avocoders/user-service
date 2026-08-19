package io.github.avocoders.userservice.handler;

import io.github.avocoders.userservice.exception.UserPersistenceException;
import io.github.avocoders.userservice.exception.UserValidationException;

public class UserExceptionHandler {
    public  void handle(UserValidationException exception){
       System.out.println("Validation error: " + exception.getMessage());
    }
    public void handle(UserPersistenceException exception){
        System.out.println("Database error: " + exception.getMessage());
    }
    public void handle(NumberFormatException exception){
        System.out.println("Input error: enter a whole number");
    }
}
