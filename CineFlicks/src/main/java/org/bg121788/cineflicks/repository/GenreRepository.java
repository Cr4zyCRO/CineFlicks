package org.bg121788.cineflicks.repository;

import org.bg121788.cineflicks.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GenreRepository extends JpaRepository<Genre, UUID> {
    Genre findByGenre(String genre);
}
