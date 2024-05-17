package org.bg121788.cineflicks.controller;

import lombok.AllArgsConstructor;
import org.bg121788.cineflicks.dto.TicketDTO;
import org.bg121788.cineflicks.dto.UserDTO;
import org.bg121788.cineflicks.entity.CinemaMovie;
import org.bg121788.cineflicks.entity.User;
import org.bg121788.cineflicks.service.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@AllArgsConstructor
@RestController
@RequestMapping("/ticket")
public class TicketController {
    private final CinemaMovieService cinemaMovieService;
    private final UserService userService;
    private final CinemaService cinemaService;
    private final MovieService movieService;
    private final TicketService ticketService;
    @GetMapping("/{cinemaMovieId}")
    public ModelAndView getTicketForm(@PathVariable UUID cinemaMovieId) {
        ModelAndView modelAndView = new ModelAndView("order-ticket");

        CinemaMovie cinemaMovie = cinemaMovieService.getById(cinemaMovieId).orElse(null);

        if (cinemaMovie != null) {
            Set<String> takenSeats = ticketService.getTakenSeats(cinemaMovieId);

            int rowCount = cinemaMovie.getCinema().getRow();
            if (rowCount > 0) {
                List<String> rowLabels = IntStream.range(0, rowCount)
                        .mapToObj(i -> String.valueOf((char) ('A' + i)))
                        .collect(Collectors.toList());

                modelAndView.addObject("cinemaMovie", cinemaMovie);
                modelAndView.addObject("rowLabels", rowLabels);
                modelAndView.addObject("takenSeats", takenSeats); // Pass taken seats to the view
            } else {
                modelAndView.addObject("error", "Invalid cinema row count.");
            }
        }

        return modelAndView;
    }

    @PostMapping("/order")
    public ResponseEntity<?> orderTickets(@RequestBody Map<String, Object> ticketData) {
        UUID cinemaMovieId = UUID.fromString((String) ticketData.get("cinemaMovieId"));
        String selectedSeats = (String) ticketData.get("selectedSeats");

        CinemaMovie cinemaMovie = cinemaMovieService.getById(cinemaMovieId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Cinema Movie ID"));

        Set<String> takenSeats = ticketService.getTakenSeats(cinemaMovieId);
        List<String> selectedSeatsList = List.of(selectedSeats.split(","));


        if (selectedSeats.isEmpty()){
            return ResponseEntity.badRequest().body("Please select a seat before ordering.");
        }

        // Validate if any selected seat is already taken
        for (String seat : selectedSeatsList) {
            if (takenSeats.contains(seat)) {
                return ResponseEntity.badRequest().body("Seat " + seat + " is already taken.");
            }
        }

        // Get the current authenticated user's details
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDTO user = userService.findByUsername(((UserDetails) authentication.getPrincipal()).getUsername());


        // Calculate the ticket price based on the number of selected seats
        Double seatPrice = cinemaMovie.getSeatPrice();
        Double totalPrice = selectedSeatsList.size() * seatPrice;

        System.err.println(seatPrice + " Total: " + totalPrice + "\n");

        // Create and populate the TicketDTO
        TicketDTO ticketDTO = new TicketDTO();
        ticketDTO.setUser(user);
        ticketDTO.setCinemaMovie(cinemaMovie);
        ticketDTO.setSelectedSeats(selectedSeats);
        ticketDTO.setPrice(totalPrice);

        // Save the ticket through the service
        ticketService.saveTicket(ticketDTO);

        return ResponseEntity.ok("Tickets ordered successfully!");
    }
}
