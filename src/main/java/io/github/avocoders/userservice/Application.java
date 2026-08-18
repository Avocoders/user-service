package io.github.avocoders.userservice;

import io.github.avocoders.userservice.dtos.UserDto;
import io.github.avocoders.userservice.mappers.UserMapper;
import io.github.avocoders.userservice.repository.HibernateUserRepository;
import io.github.avocoders.userservice.repository.UserRepository;
import io.github.avocoders.userservice.service.UserService;
import io.github.avocoders.userservice.service.UserServiceImpl;
import io.github.avocoders.userservice.validators.UserValidator;
import org.hibernate.SessionFactory;
import io.github.avocoders.userservice.config.HibernateUtil;

import java.util.List;
import java.util.Optional;

public class Application {
    public static void main(String[] args){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        UserRepository userRepository = new HibernateUserRepository(sessionFactory);
        UserMapper userMapper = new UserMapper();
        UserValidator userValidator = new UserValidator();
        UserService userService = new UserServiceImpl(userRepository, userMapper, userValidator);

        try{
            if (sessionFactory.isOpen()) {
                System.out.println("Connected");
            }
            else {
                System.out.println("Disconnected");
            }
            UserDto createdUser = userService.createUser("Name", "name6@mail.ru", 30);
            System.out.println("Created: " + createdUser);
            Optional<UserDto> foundUser = userService.getUserById(createdUser.getId());
            foundUser.ifPresentOrElse(
                    value -> System.out.println("Found: " + value),
                    () -> System.out.println("User not found")
            );
            List<UserDto> users = userService.getAllUsers();
            System.out.println("All users:");
            users.forEach(System.out::println);
            Optional<UserDto> updatedUser = userService.updateUser(
                    createdUser.getId(),
                    "Update name",
                    "updated6@mail.ru",
                    32
            );
            updatedUser.ifPresentOrElse(
                    value -> System.out.println("Updated: " + value),
                    () -> System.out.println("User not found")
            );
            boolean deleted = userService.deleteUser(createdUser.getId());
            System.out.println("Deleted: " + deleted);
            userService.getUserById(createdUser.getId())
                    .ifPresentOrElse(
                            System.out::println,
                            () -> System.out.println("User was deleted")
                    );
        }
        finally{
            HibernateUtil.shutdown();
        }


    }
}
