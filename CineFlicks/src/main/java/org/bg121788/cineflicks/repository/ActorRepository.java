package org.bg121788.cineflicks.repository;

import org.bg121788.cineflicks.entity.Actor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ActorRepository extends JpaRepository<Actor, UUID> {
    Actor findByActor(String actor);
}
