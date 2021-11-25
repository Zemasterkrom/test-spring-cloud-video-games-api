package com.rk.videogameslibraryapplication.dao;

import com.rk.videogameslibraryapplication.model.VideoGame;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.Set;

/**
 * Interface des opérations du DAO de jeux vidéo générées par le JPA Spring Boot
 */
public interface VideoGameRepository extends CrudRepository<VideoGame, Integer> {

    Set<VideoGame> findAll();

    Optional<VideoGame> findByIdOrName(Integer id, String name);

    Optional<VideoGame> findById(Integer id);

    Optional<VideoGame> findByName(String name);

    boolean existsByIdOrName(Integer id, String name);

    boolean existsById(Integer id);

    boolean existsByName(String name);

    void deleteByIdOrName(Integer id, String name);

    void deleteById(Integer id);

    void deleteByName(String name);
}
