package io.github.avocoders.userservice.repository;

import io.github.avocoders.userservice.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class HibernateUserRepositoryTest {

    private static SessionFactory sessionFactory;
    private HibernateUserRepository repository;

    @BeforeAll
    static void setUpSessionFactory() {
        sessionFactory = new Configuration()
                .configure("hibernate-test.cfg.xml")
                .buildSessionFactory();
    }

    @BeforeEach
    void setUp() {
        clearUsers();
        repository = new HibernateUserRepository(sessionFactory);
    }

    private void clearUsers() {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            session.createMutationQuery("delete from User")
                    .executeUpdate();

            transaction.commit();
        }
    }

    @AfterAll
    static void tearDownSessionFactory() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }

    @Test
    void save_shouldPersistUserAndGenerateId() {
        User user = new User ( "Alice", "alica@ya.ru", 6 );
        User saved = repository.save(user);
        assertNotNull(saved);
        assertNotNull(saved.getId());

        try (Session verificationSession = sessionFactory.openSession()) {
            User userFromDatabase = verificationSession.find(
                    User.class,
                    saved.getId()
            );
            assertNotNull(userFromDatabase);
            assertEquals("Alice", userFromDatabase.getName());
            assertEquals("alica@ya.ru", userFromDatabase.getEmail());
            assertEquals(6, userFromDatabase.getAge());
        }
    }

    @Test
    void findById_shouldReturnUser_whenUserExists() {
        User user = new User("Anna", "anna@ya.ru", 25);
        User saved = repository.save(user);
        Long id = saved.getId();
        Optional<User> foundUser = repository.findById(id);
        assertTrue(foundUser.isPresent());

        User actualUser = foundUser.orElseThrow();
        assertEquals(saved.getId(), actualUser.getId());
        assertEquals(saved.getName(), actualUser.getName());
        assertEquals(saved.getEmail(), actualUser.getEmail());
        assertEquals(saved.getAge(), actualUser.getAge());
    }

}