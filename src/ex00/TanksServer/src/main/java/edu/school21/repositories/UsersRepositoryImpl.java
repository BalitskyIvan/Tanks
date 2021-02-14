package edu.school21.repositories;

import edu.school21.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UsersRepositoryImpl extends JdbcTemplate implements UsersRepository {

    private final String tableName = "tanks.users";
    private final RowMapper<User> ROW_MAPPER;

    @Autowired
    public UsersRepositoryImpl(DataSource dataSource) {
        super(dataSource);
        this.ROW_MAPPER = (ResultSet resultSet, int row_num) -> new User(resultSet.getLong("id"),
                resultSet.getString("name"),
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
            super.update(String.format("Insert into %s (name, password) " +
                            "values ('%s', '%s');",
                    tableName, entity.getUsername(), entity.getPassword()));
        } catch ( DataAccessException ignored) {
            System.err.println("User is already exists");
        }
    }

    @Override
    public void update(User entity) {
        try {
            super.update(String.format("UPDATE %s SET name = '%s', password = '%s' WHERE id = %d;",
                    tableName, entity.getUsername(), entity.getPassword(), entity.getUserId()));
        } catch (DataAccessException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void delete(String username) {
        try {
            super.update(String.format("Delete from %s where name = '%s';", tableName, username));
        } catch (DataAccessException e) {
            System.err.println("User is not exists");
        }
    }

    @Override
    public Optional<User> findByUsername(String username) {
        try {
            return Optional.ofNullable(super.queryForObject(
                    String.format("Select * FROM %s WHERE name = '%s';",
                            tableName, username), ROW_MAPPER));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}
