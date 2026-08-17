package io.github.avocoders.userservice.repository;

import io.github.avocoders.userservice.entity.User;
import io.github.avocoders.userservice.exception.UserPersistenceException;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

public class HibernateUserRepository implements UserRepository{

    private final SessionFactory sessionFactory;
    public HibernateUserRepository(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    @Override
    public User save(User user) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(user);
            transaction.commit();
            return user;
        } catch (HibernateException exception) {
            if (transaction != null && transaction.isActive()){
                transaction.rollback();
            }
            throw new UserPersistenceException("Failed to save user", exception);
        }
    }

    @Override
    public Optional<User> findById(Long id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            User user = session.find(User.class, id);
            transaction.commit();
            return Optional.ofNullable(user);
        } catch (HibernateException exception){
            if (transaction != null && transaction.isActive()){
                transaction.rollback();
            }
            throw new UserPersistenceException("Failed to find by id = " + id, exception);
        }
    }

    @Override
    public List<User> findAll() {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            List<User> users = session.createSelectionQuery("from User order by id", User.class).getResultList();
            transaction.commit();
            return users;
        } catch (HibernateException exception){
            if (transaction != null && transaction.isActive()){
               transaction.rollback();
            }
            throw new UserPersistenceException("Failed to find All", exception);
        }
    }

    @Override
    public User update(User user) {
        return null;
    }

    @Override
    public boolean deleteById(Long id) {
        return false;
    }

}
