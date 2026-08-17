package io.github.avocoders.userservice.repository;

import io.github.avocoders.userservice.entity.User;
import java.util.List;
import java.util.Optional;

public interface UserRepository {
    User save(User user);
    Optional<User> findById(Long id);
    List<User> findAll();
    User update(User user);
    boolean deleteById(Long id);
}
