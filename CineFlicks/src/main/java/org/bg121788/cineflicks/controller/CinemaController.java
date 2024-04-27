package org.bg121788.cineflicks.controller;

import lombok.AllArgsConstructor;
import org.bg121788.cineflicks.dto.CinemaMovieDTO;
import org.bg121788.cineflicks.entity.Cinema;
import org.bg121788.cineflicks.entity.CinemaMovie;
import org.bg121788.cineflicks.entity.Movie;
import org.bg121788.cineflicks.service.CinemaMovieService;
import org.bg121788.cineflicks.service.CinemaService;
import org.bg121788.cineflicks.service.MovieService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/cinema")
public class CinemaController {
    private final CinemaService cinemaService;
    private final MovieService movieService;
    private final CinemaMovieService cinemaMovieService;

    @GetMapping("/assign-movie/{movieId}")
    public ModelAndView showAssignMovieForm(@PathVariable UUID movieId){
        ModelAndView modelAndView = new ModelAndView("assign-movie");
        Movie movie = null;

        List<Cinema> cinemas = cinemaService.getAllCinemas();
        if (movieService.getById(movieId).isPresent()){
             movie = movieService.getById(movieId).get();
        }

        modelAndView.addObject("cinemas",cinemas);
        modelAndView.addObject("movie",movie);
        modelAndView.addObject("cinemaMovies", cinemaMovieService.getAllCinemaMovies());

        return modelAndView;
    }

    @PostMapping("/assign-movie")
    public ResponseEntity<?> assignMovie(@RequestBody Map<String, Object> requestData){

        UUID cinemaId = UUID.fromString((String) requestData.get("cinemaId"));
        UUID movieId = UUID.fromString((String) requestData.get("movieId"));
        String startTime = (String) requestData.get("startTime");

        Cinema cinema = null;
        Movie movie = null;

        if(cinemaService.getById(cinemaId).isPresent()){
            cinema = cinemaService.getById(cinemaId).get();
        }


        if (movieService.getById(movieId).isPresent()){
            movie = movieService.getById(movieId).get();
        }


        LocalDateTime startDateTime = LocalDateTime.parse(startTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        int runtimeInMinutes = Integer.parseInt(movie.getRuntime().replaceAll("[^0-9]", ""));
        LocalDateTime endDateTime = startDateTime.plusMinutes(runtimeInMinutes + 30); // Adding 30 min buffer

        boolean isTimeSlotAvailable = cinemaService.isTimeSlotAvailable(cinema, startDateTime, endDateTime);

        if (!isTimeSlotAvailable) {
            return ResponseEntity.badRequest().body("Time slot is not available.");
        }

        CinemaMovieDTO cinemaMovie = new CinemaMovieDTO();
        cinemaMovie.setCinema(cinema);
        cinemaMovie.setMovie(movie);
        cinemaMovie.setStartTime(startDateTime);
        cinemaMovie.setEndTime(endDateTime);

        cinemaMovieService.saveCinemaMovie(cinemaMovie);

        return ResponseEntity.ok("Movie assigned successfully.");
    }
}
