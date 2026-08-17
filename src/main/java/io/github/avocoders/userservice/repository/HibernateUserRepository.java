package io.github.avocoders.userservice.repository;

import io.github.avocoders.userservice.entity.User;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;

public class HibernateUserRepository implements UserRepository{

    private final SessionFactory sessionFactory;
    public HibernateUserRepository(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    @Override
    public User save(User user) {
        return null;
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<User> findAll() {
        return List.of();
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
