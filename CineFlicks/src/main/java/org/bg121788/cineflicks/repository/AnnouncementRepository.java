package org.bg121788.cineflicks.repository;

import jakarta.transaction.Transactional;
import org.bg121788.cineflicks.entity.Announcement;
import org.bg121788.cineflicks.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, UUID> {
    Announcement findByMovie(Movie movie);

    @Transactional
    @Modifying
    @Query("DELETE FROM Announcement a WHERE a.movie = :movie")
    void deleteAllByMovie(Movie movie);
}
