package io.github.avocoders.userservice;

import io.github.avocoders.userservice.config.HibernateUtil;
import io.github.avocoders.userservice.controller.ConsoleController;
import io.github.avocoders.userservice.handler.UserExceptionHandler;
import io.github.avocoders.userservice.mappers.UserMapper;
import io.github.avocoders.userservice.repository.HibernateUserRepository;
import io.github.avocoders.userservice.repository.UserRepository;
import io.github.avocoders.userservice.service.UserService;
import io.github.avocoders.userservice.service.UserServiceImpl;
import io.github.avocoders.userservice.validators.UserValidator;
import org.hibernate.SessionFactory;

import java.util.Scanner;

public class Application {
    public static void main(String[] args){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        UserRepository userRepository = new HibernateUserRepository(sessionFactory);
        UserMapper userMapper = new UserMapper();
        UserValidator userValidator = new UserValidator();
        UserService userService = new UserServiceImpl(userRepository, userMapper, userValidator);
        UserExceptionHandler exceptionHandler = new UserExceptionHandler();

        try (Scanner scanner = new Scanner(System.in)){
            ConsoleController controller = new ConsoleController(userService, exceptionHandler, scanner);
            controller.run();
        }
        finally{
            HibernateUtil.shutdown();
        }


    }
}
