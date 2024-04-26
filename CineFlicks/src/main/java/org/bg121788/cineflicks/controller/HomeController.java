package org.bg121788.cineflicks.controller;

import lombok.AllArgsConstructor;
import org.bg121788.cineflicks.entity.CinemaMovie;
import org.bg121788.cineflicks.service.CinemaMovieService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/")
public class HomeController {
    private final CinemaMovieService cinemaMovieService;

    @GetMapping
    public ModelAndView mainView(@RequestParam(value = "sortBy", required = false) String sortBy){
        ModelAndView modelAndView = new ModelAndView("/home");

        List<CinemaMovie> movies = cinemaMovieService.getMoviesForThisWeek(sortBy);
        modelAndView.addObject("movies", movies);

        return modelAndView;
    }

}
