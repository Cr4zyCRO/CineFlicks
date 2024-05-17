package org.bg121788.cineflicks.repository;

import org.bg121788.cineflicks.entity.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, UUID> {
    Collection<Ticket> findByCinemaMovieId(UUID cinemaMovieId);

    @Query("SELECT t FROM Ticket t JOIN t.cinemaMovie cm WHERE t.user.username = :username AND cm.startTime < :currentTime")
    List<Ticket> findWatchedTicketsBefore(String username, LocalDateTime currentTime);

    @Query("SELECT t FROM Ticket t JOIN t.cinemaMovie cm WHERE t.user.username = :username AND cm.startTime > :currentTime")
    List<Ticket> findUpcomingTicketsAfter(String username, LocalDateTime currentTime);


}
