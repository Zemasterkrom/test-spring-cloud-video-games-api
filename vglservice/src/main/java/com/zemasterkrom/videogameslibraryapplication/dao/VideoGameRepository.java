package com.zemasterkrom.videogameslibraryapplication.dao;

import com.zemasterkrom.videogameslibraryapplication.model.VideoGame;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;
import org.springframework.lang.NonNullApi;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;
import java.util.Optional;
import java.util.Set;

/**
 * Interface des opérations du DAO de jeux vidéo générées par le JPA Spring Boot
 */
public interface VideoGameRepository extends CrudRepository<VideoGame, Integer> {

    @NonNull
    Set<VideoGame> findAll();

    Optional<VideoGame> findByIdOrName(Integer id, String name);

    @NonNull
    Optional<VideoGame> findById(@NonNull Integer id);

    Optional<VideoGame> findByName(String name);

    boolean existsByIdOrName(Integer id, String name);

    boolean existsById(@NonNull Integer id);

    boolean existsByName(String name);

    void deleteByIdOrName(Integer id, String name);

    void deleteById(@NonNull Integer id);

    void deleteByName(String name);
}
