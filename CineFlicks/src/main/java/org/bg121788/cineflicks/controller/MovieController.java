package org.bg121788.cineflicks.controller;

import lombok.AllArgsConstructor;
import org.bg121788.cineflicks.entity.Movie;
import org.bg121788.cineflicks.service.AnnouncementService;
import org.bg121788.cineflicks.service.CinemaMovieService;
import org.bg121788.cineflicks.service.MovieService;
import org.bg121788.cineflicks.service.TicketService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/movie")
@AllArgsConstructor
public class MovieController {
    private final MovieService movieService;
    private final AnnouncementService announcementService;
    private final CinemaMovieService cinemaMovieService;
    private final TicketService ticketService;
    @PostMapping("/{id}")
    public ModelAndView deleteMovie(@PathVariable UUID id) {
        // Check if the movie exists
        Optional<Movie> movie = movieService.getById(id);
        announcementService.deleteMovieAnnouncement(movie);

        cinemaMovieService.deleteMovieInCinemaMovie(movie);


        if (movie.isPresent()) {
            movieService.deleteMovie(id); // Delete the movie
        }

        return new ModelAndView("redirect:/profile/admin");
    }

    @PostMapping("/{id}/rating")
    public ModelAndView updateRating(@PathVariable UUID id, @RequestParam Integer rating) {
        // Check if the movie exists
        Optional<Movie> movieOptional = movieService.getById(id);

        // Delete the movie
        movieOptional.ifPresent(movie -> movieService.updateRating(movie, rating));

        return new ModelAndView("redirect:/profile");
    }

    @GetMapping("/search")
    public ResponseEntity<List<Movie>> searchMovies(@RequestParam String query) {
        List<Movie> movies = movieService.searchMovies(query);
        return ResponseEntity.ok(movies);
    }



}
