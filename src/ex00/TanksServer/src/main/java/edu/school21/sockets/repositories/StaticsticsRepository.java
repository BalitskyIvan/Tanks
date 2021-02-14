package edu.school21.sockets.repositories;

import edu.school21.models.Statistic;

import java.util.Optional;

public interface StaticsticsRepository extends CrudRepository<Statistic>{
    Optional<Statistic> findByUsername(String name);
}
