package org.bg121788.cineflicks.repository;

import org.bg121788.cineflicks.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface MovieRepository extends JpaRepository<Movie, UUID> {

    boolean existsByTitle(String title);

    @Query("SELECT m FROM Movie m WHERE m.imdb_votes > 500 ORDER BY m.imdb_rating DESC LIMIT 10")
    List<Movie> findTop10ByOrderByImdb_ratingDesc();

}
