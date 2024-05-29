package org.bg121788.cineflicks.repository;

import org.bg121788.cineflicks.entity.Announcement;
import org.bg121788.cineflicks.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, UUID> {

    Announcement findByMovie(Movie movie);
}
