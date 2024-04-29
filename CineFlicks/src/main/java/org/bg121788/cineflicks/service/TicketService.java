package org.bg121788.cineflicks.service;

import lombok.AllArgsConstructor;
import org.bg121788.cineflicks.dto.TicketDTO;
import org.bg121788.cineflicks.repository.TicketRepository;
import org.springframework.stereotype.Service;
import org.bg121788.cineflicks.entity.Ticket;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class TicketService {
    private final TicketRepository ticketRepository;


    public Set<String> getTakenSeats(UUID cinemaMovieId) {
        return ticketRepository.findByCinemaMovieId(cinemaMovieId).stream()
                .map(Ticket::getSelectedSeats) // Get selected seats from each ticket
                .flatMap(seats -> Stream.of(seats.split(","))) // Flatten the seats list
                .collect(Collectors.toSet()); // Collect into a set
    }

    public void saveTicket(TicketDTO ticketDTO) {
        Ticket ticket = new Ticket();
        ticket.setUser(ticketDTO.getUser());
        ticket.setPrice(ticketDTO.getPrice());
        ticket.setCinemaMovie(ticketDTO.getCinemaMovie());
        ticket.setSelectedSeats(ticketDTO.getSelectedSeats());

        ticketRepository.save(ticket);
    }
}
