package org.bg121788.cineflicks.repository;

import org.bg121788.cineflicks.entity.Director;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface DirectorRepository extends JpaRepository<Director, UUID> {
    Director findByDirector(String director);
}
