package org.bg121788.cineflicks.repository;

import jakarta.persistence.ManyToOne;
import jakarta.transaction.Transactional;
import org.bg121788.cineflicks.entity.Cinema;
import org.bg121788.cineflicks.entity.CinemaMovie;
import org.bg121788.cineflicks.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface CinemaMovieRepository extends JpaRepository<CinemaMovie, UUID> {

    @Query("SELECT cm FROM CinemaMovie cm WHERE cm.startTime BETWEEN :start AND :end ORDER BY cm.startTime ASC")
    List<CinemaMovie> findAllByStartTimeBetween(LocalDateTime start, LocalDateTime end);

    List<CinemaMovie> findByCinema(Cinema cinema);

    @Query("SELECT cm FROM CinemaMovie cm WHERE cm.startTime > :start ORDER BY cm.startTime ASC")
    List<CinemaMovie> findAllByStartTimeBeforeNow(LocalDateTime start);

    boolean existsByMovie(Movie movie);

    @Query("SELECT cm FROM CinemaMovie cm WHERE cm.movie = :movie")
    List<CinemaMovie> findAllByMovie(Movie movie);

    @Transactional
    @Modifying
    @Query("DELETE FROM CinemaMovie cm WHERE cm.movie = :movie")
    void deleteAllByMovie(Movie movie);
}
