package io.github.avocoders.userservice.controller;

import io.github.avocoders.userservice.handler.UserExceptionHandler;
import io.github.avocoders.userservice.service.UserService;
import java.util.Scanner;

public class ConsoleController {
    private final UserService userService;
    private final UserExceptionHandler exceptionHandler;
    private final Scanner scanner;

    public ConsoleController(UserService userService, UserExceptionHandler exceptionHandler, Scanner scanner) {
        this.userService = userService;
        this.exceptionHandler = exceptionHandler;
        this.scanner = scanner;
    }
}
