package org.bg121788.cineflicks.repository;

import org.bg121788.cineflicks.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.UUID;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, UUID> {
    Collection<Ticket> findByCinemaMovieId(UUID cinemaMovieId);
}
