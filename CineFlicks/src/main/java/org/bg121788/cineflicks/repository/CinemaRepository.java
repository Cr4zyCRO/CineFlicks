package org.bg121788.cineflicks.repository;

import org.bg121788.cineflicks.entity.Cinema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface CinemaRepository extends JpaRepository<Cinema, UUID> {
}
