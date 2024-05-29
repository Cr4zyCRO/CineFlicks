package org.bg121788.cineflicks.controller;

import lombok.AllArgsConstructor;
import org.bg121788.cineflicks.dto.MovieDTO;
import org.bg121788.cineflicks.service.MovieService;
import org.bg121788.cineflicks.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("/profile/admin")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class AdminController {
    private final UserService userService;
    private final MovieService movieService;

    @GetMapping
    public ModelAndView adminWebsite(){
        ModelAndView modelAndView = new ModelAndView("admin");
        modelAndView.addObject("movies", movieService.getAllMovies());
        modelAndView.addObject("users", userService.getAllUsers());
        modelAndView.addObject("topRated", movieService.getTop10MostRated());
        modelAndView.addObject("topViewed", movieService.getTop10MostViewed());



        return modelAndView;
    }

    @GetMapping("/add-movie")
    public ModelAndView addMovie(){
        return new ModelAndView("/add-movies");
    }

    @PostMapping("/add-movie")
    public ResponseEntity<?> addMovie(@RequestBody Map<String, String> requestBody) {
        String movieTitle = requestBody.get("title");

        // Call OMDb API to get movie details
        RestTemplate restTemplate = new RestTemplate();
        String apiUrl = "http://www.omdbapi.com/?apikey=481bb95e&t=" + movieTitle;
        Map omdbResponse = restTemplate.getForObject(apiUrl, Map.class);

        if (omdbResponse == null || omdbResponse.containsKey("Error")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "Movie not found"));
        }

        MovieDTO movieDTO = new MovieDTO();

        movieDTO.setTitle((String) omdbResponse.get("Title"));
        movieDTO.setRated((String) omdbResponse.get("Rated"));
        movieDTO.setRuntime((String) omdbResponse.get("Runtime"));
        movieDTO.setPlot((String) omdbResponse.get("Plot"));
        movieDTO.setCountry((String) omdbResponse.get("Country"));
        movieDTO.setAwards((String) omdbResponse.get("Awards"));
        movieDTO.setPoster((String) omdbResponse.get("Poster"));
        movieDTO.setImdb_id((String) omdbResponse.get("imdbID"));

        movieDTO.setImdb_rating(praseDouble(omdbResponse.get("imdbRating")));

        movieDTO.setYear(praseInteger(omdbResponse.get("Year")));
        movieDTO.setMetascore(praseInteger(omdbResponse.get("Metascore")));
        movieDTO.setImdb_votes(praseInteger(omdbResponse.get("imdbVotes")));

        movieDTO.setGenre(Arrays.asList(((String) omdbResponse.get("Genre")).split(",\\s*")));
        movieDTO.setDirector( Arrays.asList(((String) omdbResponse.get("Director")).split(",\\s*")));
        movieDTO.setWriter(Arrays.asList(((String) omdbResponse.get("Writer")).split(",\\s*")));
        movieDTO.setActor(Arrays.asList(((String) omdbResponse.get("Actors")).split(",\\s*")));
        movieDTO.setLanguage(Arrays.asList(((String) omdbResponse.get("Language")).split(",\\s*")));


        boolean movieAdded = movieService.addNewMovie(movieDTO);

        if (movieAdded) {
            return ResponseEntity.ok(Map.of("message", "Movie added successfully"));
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("message", "Movie already exists"));
        }
    }

    private Integer praseInteger(Object obj){//JSON object
        if (obj == null) {
            return null;
        }

        try {
            String cleanValue = obj.toString().replaceAll(",", ""); // Remove commas
            return Integer.valueOf(cleanValue);
        } catch (NumberFormatException e) {
            return null;
        }
    }
    private Double praseDouble(Object obj){//JSON object
        try {
            return Double.valueOf(obj.toString());
        } catch (NumberFormatException e) {
            return null;
        }

    }
}
