package edu.school21.sockets.repositories;

import edu.school21.sockets.exceptions.EntityNotFoundException;
import edu.school21.sockets.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class UsersRepositoryImpl extends JdbcTemplate implements UsersRepository {
    private final String tableName = "tanks.users";
    private final RowMapper<User> ROW_MAPPER;

    @Autowired
    public UsersRepositoryImpl(DataSource dataSource) {
        super(dataSource);
        this.ROW_MAPPER = (ResultSet resultSet, int row_num) -> new User(resultSet.getLong("id"),
                resultSet.getString("username"),
                resultSet.getString("password"));
    }

    @Override
    public Optional<User> findById(Long id) {
        try {
            return Optional.ofNullable(super.queryForObject(
                    String.format("Select * FROM %s WHERE id = '%d';", tableName, id), ROW_MAPPER));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<User> findAll() {
        try {
            return super.query(String.format("Select * from %s;", tableName), ROW_MAPPER);
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public void save(User entity) {
        try {
            super.update(String.format("Insert into %s (username, password) " +
                            "values ('%s', '%s');",
                    tableName, entity.getUsername(), entity.getPassword()));
        } catch ( DataAccessException ignored) {
            System.err.println("User is already exists");
        }
    }

    @Override
    public void update(User entity) {
        try {
            super.update(String.format("UPDATE %s SET username = '%s', password = '%s' WHERE id = %d;",
                    tableName, entity.getUsername(), entity.getPassword(), entity.getUserId()));
        } catch (DataAccessException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void delete(String username) {
        try {
            super.update(String.format("Delete from %s where username = '%s';", tableName, username));
        } catch (DataAccessException e) {
            System.err.println("User is not exists");
        }
    }

    @Override
    public Optional<User> findByUsername(String username) {
        try {
            return Optional.ofNullable(super.queryForObject(
                    String.format("Select * FROM %s WHERE username = '%s';",
                            tableName, username), ROW_MAPPER));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Long findId(String login) {
        User person = null;
        try {
            person = super.queryForObject(String.format("select * from %s where username = '%s'", tableName, login), ROW_MAPPER);
        } catch (DataAccessException dataAccessException) {
            System.out.println("here" + dataAccessException.getMessage());
        }
        assert person != null;
        return person.getUserId();
    }
}
