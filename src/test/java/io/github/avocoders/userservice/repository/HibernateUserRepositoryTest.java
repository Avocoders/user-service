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

import java.util.List;
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

    @Test
    void findById_shouldReturnEmpty_whenUserDoesNotExist() {
        Long id = 55L;
        Optional<User> foundUser = repository.findById(id);
        assertTrue(foundUser.isEmpty());
    }

    @Test
    void findAll_shouldReturnAllUsers() {
        User user1 = new User("Anna", "anna@ya.ru", 25);
        User user2 = new User ( "Alice", "alica@ya.ru", 6 );
        User saved1 = repository.save(user1);
        User saved2 = repository.save(user2);

        List<User> users = repository.findAll();

        assertEquals(2, users.size());
        assertEquals( saved1.getId(), users.get(0).getId() );
        assertEquals( saved2.getId(), users.get(1).getId() );
    }

    @Test
    void findAll_shouldReturnEmptyList_whenUsersDoNotExist() {
        List<User> users = repository.findAll();
        assertTrue(users.isEmpty());
    }

    @Test
    void update_shouldUpdateUser_whenUserExists() {
        User user = new User("Anna", "anna@ya.ru", 25);
        User saved = repository.save(user);
        saved.setName("Vika");
        saved.setEmail("vika@ya.ru");
        saved.setAge(5);
        User updatedUser = repository.update(saved);

        assertNotNull(updatedUser);
        assertEquals(saved.getId(), updatedUser.getId());
        assertEquals("Vika", updatedUser.getName());
        assertEquals("vika@ya.ru", updatedUser.getEmail());
        assertEquals(5, updatedUser.getAge());

        try (Session session = sessionFactory.openSession()) {
            Long userId = updatedUser.getId();
            User found = session.find(User.class, userId);

            assertNotNull(found);
            assertEquals(saved.getId(), found.getId());
            assertEquals("Vika", found.getName());
            assertEquals("vika@ya.ru", found.getEmail());
            assertEquals(5, found.getAge());

        }
    }

    @Test
    void deleteById_shouldReturnTrueAndDeleteUser_whenUserExists() {
        User user = new User("Anna", "anna@ya.ru", 25);
        User saved = repository.save(user);
        Long id = saved.getId();
        boolean deleted = repository.deleteById(id);
        assertTrue(deleted);

        try (Session session = sessionFactory.openSession()) {
            User found = session.find(User.class, id);

            assertNull(found);
        }
    }

    @Test
    void deleteById_shouldReturnFalse_whenUserDoesNotExist() {
        Long id = 50L;
        boolean deleted = repository.deleteById(id);
        assertFalse(deleted);
    }

}