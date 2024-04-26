package org.bg121788.cineflicks.repository;

import org.bg121788.cineflicks.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface MovieRepository extends JpaRepository<Movie, UUID> {

    List<Movie> findAll();

    boolean existsByTitle(String title);
}
