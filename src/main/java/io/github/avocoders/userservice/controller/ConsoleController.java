package io.github.avocoders.userservice.controller;

import io.github.avocoders.userservice.dtos.UserDto;
import io.github.avocoders.userservice.exception.UserPersistenceException;
import io.github.avocoders.userservice.exception.UserValidationException;
import io.github.avocoders.userservice.handler.UserExceptionHandler;
import io.github.avocoders.userservice.service.UserService;

import java.util.List;
import java.util.Optional;
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
    private void printMenu(){
        System.out.println(
                "1 - Create user\n" +
                "2 - Find user by id\n" +
                "3 - Find all users\n" +
                "4 - Update user\n" +
                "5 - Delete user\n" +
                "0 - Exit\n" +
                "Choose an action: "
        );
    }
    public void run(){
        printMenu();
        String command = scanner.nextLine().trim();
        while(!command.equals("0")){
            System.out.println("You selected: " + command);
            try {
                switch (command){
                    case "1" -> createUser();
                    case "2" -> findUserById();
                    case "3" -> getAllUsers();
                    case "4" -> System.out.println("Update user");
                    case "5" -> System.out.println("Delete user");
                    default -> System.out.println("Not found");
                }
            } catch (NumberFormatException exception){
                exceptionHandler.handle(exception);
            } catch (UserValidationException exception){
                exceptionHandler.handle(exception);
            } catch (UserPersistenceException exception){
                exceptionHandler.handle(exception);
            }
            printMenu();
            command = scanner.nextLine().trim();
        }
        System.out.println("Application stopped");
    }

    private void createUser(){
        System.out.println("Enter name: ");
        String name = scanner.nextLine();
        System.out.println("Enter email: ");
        String email = scanner.nextLine().trim();
        System.out.println("Enter age: ");
        String age = scanner.nextLine().trim();
        UserDto newUser = userService.createUser(name, email, Integer.valueOf(age));
        System.out.println("Created user:\n" + newUser);
    }

    private void findUserById(){
        System.out.println("Enter user id: ");
        String id = scanner.nextLine().trim();
        Optional<UserDto> foundUser = userService.getUserById(Long.valueOf(id));
        foundUser.ifPresentOrElse(System.out::println,
                () -> System.out.println("User not found"));
    }

    private void getAllUsers(){
        List<UserDto> users = userService.getAllUsers();
        if(users.isEmpty()){
            System.out.println("No users found");
        } else {
            users.forEach(System.out::println);
        }
    }



}
