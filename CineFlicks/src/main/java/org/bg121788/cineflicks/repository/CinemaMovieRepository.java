package org.bg121788.cineflicks.repository;

import org.bg121788.cineflicks.entity.CinemaMovie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface CinemaMovieRepository extends JpaRepository<CinemaMovie, UUID> {
    List<CinemaMovie> findAllByStartTimeBetween(LocalDateTime start, LocalDateTime end);
    @Query("SELECT cm FROM CinemaMovie cm WHERE cm.startTime BETWEEN :start AND :end ORDER BY cm.startTime ASC")
    List<CinemaMovie> findAllByWeekSorted(LocalDateTime start, LocalDateTime end);
}
