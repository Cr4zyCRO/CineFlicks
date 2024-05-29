package org.bg121788.cineflicks.controller;

import lombok.AllArgsConstructor;
import org.bg121788.cineflicks.entity.Movie;
import org.bg121788.cineflicks.service.MovieService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/movie")
@AllArgsConstructor
public class MovieController {
    private final MovieService movieService;
    @PostMapping("/{id}")
    public ModelAndView deleteMovie(@PathVariable UUID id) {
        // Check if the movie exists
        Optional<Movie> movieOptional = movieService.getById(id);

        if (movieOptional.isPresent()) {
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

        return new ModelAndView("redirect:/profile/admin");
    }



}
