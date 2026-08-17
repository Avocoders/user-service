package io.github.avocoders.userservice;

import io.github.avocoders.userservice.entity.User;
import io.github.avocoders.userservice.repository.HibernateUserRepository;
import io.github.avocoders.userservice.repository.UserRepository;
import org.hibernate.SessionFactory;
import io.github.avocoders.userservice.config.HibernateUtil;

import java.util.List;
import java.util.Optional;

public class Application {
    public static void main(String[] args){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        UserRepository userRepository = new HibernateUserRepository(sessionFactory);
        User user = new User("Name", "name3@email.ru", 30);
        try{
            if (sessionFactory.isOpen()) {
                System.out.println("Connected");
            }
            else {
                System.out.println("Disconnected");
            }
            User savedUser = userRepository.save(user);
            System.out.println(savedUser);

            Optional<User> findUser = userRepository.findById(savedUser.getId());
            findUser.ifPresentOrElse(System.out::println,
                        () -> System.out.println("User not found"));

            List<User> findAll = userRepository.findAll();
            findAll.forEach(System.out::println);

            savedUser.setName("Name2");
            savedUser.setAge(32);
            User updatedUser = userRepository.update(savedUser);
            System.out.println("Updated: " + updatedUser);

            Optional<User> findUser2 = userRepository.findById(updatedUser.getId());
            findUser2.ifPresentOrElse(System.out::println,
                    () -> System.out.println("User not found"));

        }
        finally{
            HibernateUtil.shutdown();
        }


    }
}
