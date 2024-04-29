package org.bg121788.cineflicks.controller;

import lombok.AllArgsConstructor;
import org.bg121788.cineflicks.entity.CinemaMovie;
import org.bg121788.cineflicks.service.CinemaMovieService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/")
public class HomeController {
    private final CinemaMovieService cinemaMovieService;

    @GetMapping
    public ModelAndView mainView(@RequestParam(value = "sortBy", required = false) String sortBy) {
        ModelAndView modelAndView = new ModelAndView("home");


        // Fetch the movies for this week
        List<CinemaMovie> movies = cinemaMovieService.getMoviesForThisWeek(sortBy);


        Map<String, Map<LocalDate, List<CinemaMovie>>> groupedMovies = movies.stream()
                .collect(Collectors.groupingBy(
                        cm -> cm.getMovie().getTitle(),
                        Collectors.groupingBy(cm -> cm.getStartTime().toLocalDate())
                ));

        modelAndView.addObject("groupedMovies", groupedMovies);

        return modelAndView;
    }

}
