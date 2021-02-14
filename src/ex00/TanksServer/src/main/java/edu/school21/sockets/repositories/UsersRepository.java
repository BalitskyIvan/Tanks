package edu.school21.sockets.repositories;

import edu.school21.sockets.exceptions.EntityNotFoundException;
import edu.school21.sockets.models.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public interface UsersRepository extends CrudRepository<User> {

    void save(User entity);

    void update(User entity);

    Optional<User> findByUsername(String username);

    Long findId(String login);

    Optional<User> findById(Long id);

}
