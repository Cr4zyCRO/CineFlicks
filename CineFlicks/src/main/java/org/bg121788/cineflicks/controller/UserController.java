package org.bg121788.cineflicks.controller;

import lombok.Data;
import org.bg121788.cineflicks.dto.UserDTO;
import org.bg121788.cineflicks.entity.Ticket;
import org.bg121788.cineflicks.service.MovieService;
import org.bg121788.cineflicks.service.TicketService;
import org.bg121788.cineflicks.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.UUID;

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

    @GetMapping("/edit")
    public ModelAndView editProfile(@AuthenticationPrincipal UserDetails userDetails ){
        ModelAndView modelAndView = new ModelAndView("edit-profile");

        // Fetch user info from UserService
        UserDTO userDTO = userService.findByUsername(userDetails.getUsername());
        modelAndView.addObject("user", userDTO);

        return modelAndView;
    }

    @PostMapping("/update")
    public ResponseEntity<Void> updateProfile(@RequestBody UserDTO userDTO, @RequestParam String passwordConfirm) {
        try {
            if(userDTO.getPassword().equals(passwordConfirm))
                userService.updateUser(userDTO, passwordConfirm);
            else
                throw new RuntimeException("Passwords do not match");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID userId) {
        ticketService.deleteUserTickets(userService.getUserById(userId));
        userService.deleteUserById(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
