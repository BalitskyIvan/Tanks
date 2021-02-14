package edu.school21.sockets.repositories;

import edu.school21.models.Statistic;
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
public class StatisticRepositoryImpl extends JdbcTemplate implements StaticsticsRepository {

    private final String tableName = "tanks.statistics";
    private final RowMapper<Statistic> ROW_MAPPER;

    @Autowired
    public StatisticRepositoryImpl(DataSource dataSource) {
        super(dataSource);
        this.ROW_MAPPER = (ResultSet rs, int row_num) ->
                new Statistic(rs.getLong("id"),
                        rs.getString("username"),
                        rs.getString("enemyname"),
                        rs.getInt("shots"),
                        rs.getInt("hits"));
    }
    @Override
    public Optional<Statistic> findById(Long id) {
        try {
            return Optional.ofNullable(super.queryForObject(
                    String.format("Select * FROM %s WHERE id = '%d';", tableName, id), ROW_MAPPER));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Statistic> findAll() {
        try {
            return super.query(String.format("Select * from %s;", tableName), ROW_MAPPER);
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public void save(Statistic entity) {
        try {
            super.update(String.format("Insert into %s (username, enemyname, shots, hits, misses) " +
                            "values ('%s', '%s', '%d', '%d', '%d');",
                    tableName, entity.getUser(), entity.getEnemy(),
                    entity.getShots(), entity.getHits(), entity.getMisses()));
        } catch ( DataAccessException ignored) {
            System.err.println("Stat is already exists");
        }
    }

    @Override
    public void update(Statistic entity) {
        try {
            super.update(String.format("UPDATE %s SET username = '%s', enemyname = '%s' WHERE id = %d;",
                    tableName, entity.getUser(), entity.getEnemy(), entity.getStatId()));
        } catch (DataAccessException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void delete(String name) {
        try {
            super.update(String.format("Delete from %s where username = '%s';", tableName, name));
        } catch (DataAccessException e) {
            System.err.println("Stat is not exists");
        }
    }

    @Override
    public Optional<Statistic> findByUsername(String name) {
        try {
            return Optional.ofNullable(super.queryForObject(
                    String.format("Select * FROM %s WHERE username = '%s';",
                            tableName, name), ROW_MAPPER));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}
