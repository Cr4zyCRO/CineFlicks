package org.bg121788.cineflicks.repository;

import jakarta.transaction.Transactional;
import org.bg121788.cineflicks.entity.CinemaMovie;
import org.bg121788.cineflicks.entity.Movie;
import org.bg121788.cineflicks.entity.Ticket;
import org.bg121788.cineflicks.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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


    @Transactional
    @Modifying
    @Query("DELETE FROM Ticket t WHERE t.user = :user")
    void deleteAllByUser(User user);

    @Transactional
    @Modifying
    @Query("DELETE FROM Ticket t WHERE t.cinemaMovie = :cinemaMovie")
    void deleteAllByMovie(CinemaMovie cinemaMovie);
}
