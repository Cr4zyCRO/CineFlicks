package org.bg121788.cineflicks.controller;

import lombok.Data;
import org.bg121788.cineflicks.dto.UserDTO;
import org.bg121788.cineflicks.entity.Ticket;
import org.bg121788.cineflicks.service.MovieService;
import org.bg121788.cineflicks.service.TicketService;
import org.bg121788.cineflicks.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.List;

@Data
@RestController
@RequestMapping("/profile")
public class UserController {
    private final UserService userService;
    private final MovieService movieService;
    private final TicketService ticketService;

    @GetMapping
    public ModelAndView profileWebsite(@AuthenticationPrincipal UserDetails userDetails) {
        ModelAndView modelAndView = new ModelAndView("profile");

        // Fetch user info from UserService
        UserDTO userDTO = userService.findByUsername(userDetails.getUsername());
        modelAndView.addObject("user", userDTO);

        // Fetch watched tickets
        List<Ticket> watchedTickets = ticketService.getWatchedTickets(userDetails.getUsername());
        modelAndView.addObject("watchedTickets", watchedTickets);

        // Fetch upcoming tickets
        List<Ticket> upcomingTickets = ticketService.getUpcomingTickets(userDetails.getUsername());
        modelAndView.addObject("upcomingTickets", upcomingTickets);

        return modelAndView;
    }
}
