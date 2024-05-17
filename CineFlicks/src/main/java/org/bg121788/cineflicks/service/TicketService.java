package org.bg121788.cineflicks.service;

import lombok.AllArgsConstructor;
import org.bg121788.cineflicks.dto.TicketDTO;
import org.bg121788.cineflicks.entity.User;
import org.bg121788.cineflicks.repository.TicketRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.bg121788.cineflicks.entity.Ticket;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class TicketService {
    private final TicketRepository ticketRepository;
    private final UserService userService;


    public Set<String> getTakenSeats(UUID cinemaMovieId) {
        return ticketRepository.findByCinemaMovieId(cinemaMovieId).stream()
                .map(Ticket::getSelectedSeats) // Get selected seats from each ticket
                .flatMap(seats -> Stream.of(seats.split(","))) // Flatten the seats list
                .collect(Collectors.toSet()); // Collect into a set
    }

    public void saveTicket(TicketDTO ticketDTO) {
        Ticket ticket = new Ticket();
        ticket.setUser(userService.convertToEntity(ticketDTO.getUser()));
        ticket.setPrice(ticketDTO.getPrice());
        ticket.setCinemaMovie(ticketDTO.getCinemaMovie());
        ticket.setSelectedSeats(ticketDTO.getSelectedSeats());

        ticketRepository.save(ticket);
    }

    public List<Ticket> getWatchedTickets(String username) {
        LocalDateTime currentTime = LocalDateTime.now();
        return ticketRepository.findWatchedTicketsBefore(username, currentTime);
    }

    public List<Ticket> getUpcomingTickets(String username) {
        LocalDateTime currentTime = LocalDateTime.now();
        return ticketRepository.findUpcomingTicketsAfter(username, currentTime);
    }
}
