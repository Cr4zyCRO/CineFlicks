package org.bg121788.cineflicks.controller;

import lombok.AllArgsConstructor;
import org.bg121788.cineflicks.entity.CinemaMovie;
import org.bg121788.cineflicks.entity.Language;
import org.bg121788.cineflicks.entity.Genre;
import org.bg121788.cineflicks.service.AnnouncementService;
import org.bg121788.cineflicks.service.CinemaMovieService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


@RestController
@AllArgsConstructor
@RequestMapping("/")
public class HomeController {
    private final CinemaMovieService cinemaMovieService;
    private final AnnouncementService announcementService;

    @GetMapping
    public ModelAndView mainView(
            @RequestParam(value = "sortDir", required = false, defaultValue = "ASC") String sortDir,
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @RequestParam(value = "languages", required = false) List<String> languages,
            @RequestParam(value = "ratings", required = false) List<String> ratings,
            @RequestParam(value = "genres", required = false) List<String> genres
    ) {
        ModelAndView modelAndView = new ModelAndView("home");

        // Fetch the movies for this week
        List<CinemaMovie> movies = cinemaMovieService.getMoviesForThisWeek(sortDir);

        modelAndView.addObject("languages", extractLanguages(movies));
        modelAndView.addObject("ratings", extractRatings(movies));
        modelAndView.addObject("genres", extractGenres(movies));

        // Apply filters
        if (languages != null && !languages.isEmpty()) {
            movies = filterMoviesByLanguages(movies, languages);
        }

        if (ratings != null && !ratings.isEmpty()) {
            movies = filterMoviesByRatings(movies, ratings);
        }

        if (genres != null && !genres.isEmpty()) {
            movies = filterMoviesByGenres(movies, genres);
        }

        // Sort the movies
        sortMovies(movies, sortBy, sortDir);

        // Group the movies
        Map<String, Map<LocalDate, List<CinemaMovie>>> groupedMovies = groupMoviesByTitleAndDate(movies);

        // Add filter options and grouped movies to the view
        modelAndView.addObject("groupedMovies", groupedMovies);
        modelAndView.addObject("sortDir", sortDir);
        modelAndView.addObject("announcements", announcementService.getAllAnnouncements());



        return modelAndView;
    }

    private List<CinemaMovie> filterMoviesByLanguages(List<CinemaMovie> movies, List<String> languages) {
        return movies.stream()
                .filter(movie -> movieContainsAnyLanguage(movie, languages))
                .collect(Collectors.toList());
    }

    private List<CinemaMovie> filterMoviesByRatings(List<CinemaMovie> movies, List<String> ratings) {
        return movies.stream()
                .filter(movie -> ratings.contains(movie.getMovie().getRated()))
                .collect(Collectors.toList());
    }

    private List<CinemaMovie> filterMoviesByGenres(List<CinemaMovie> movies, List<String> genres) {
        return movies.stream()
                .filter(movie -> movieContainsAnyGenre(movie, genres))
                .collect(Collectors.toList());
    }

    private void sortMovies(List<CinemaMovie> movies, String sortBy, String sortDir) {
        if (sortBy != null && !sortBy.isEmpty()) {
            Comparator<CinemaMovie> comparator = switch (sortBy) {
                case "imdbRating" -> Comparator.comparing(cm -> cm.getMovie().getImdb_rating());
                case "imdbVotes" -> Comparator.comparing(cm -> cm.getMovie().getImdb_votes());
                case "runtime" -> Comparator.comparing(cm -> cm.getMovie().getRuntime());
                default ->
                    // Default sorting by movie title
                        Comparator.comparing(cm -> cm.getMovie().getTitle());
            };

            if (comparator != null) {
                movies.sort(comparator);
            }

            // Reverse the sorting order if sortDir is DESC
            if ("DESC".equals(sortDir)) {
                Collections.reverse(movies);
            }
        }
    }

    private Map<String, Map<LocalDate, List<CinemaMovie>>> groupMoviesByTitleAndDate(List<CinemaMovie> movies) {
        return movies.stream()
                .collect(Collectors.groupingBy(
                        cm -> cm.getMovie().getTitle(),
                        LinkedHashMap::new,
                        Collectors.groupingBy(cm -> cm.getStartTime().toLocalDate())
                ));
    }

    private Set<String> extractLanguages(List<CinemaMovie> movies) {
        return movies.stream()
                .flatMap(cm -> cm.getMovie().getLanguage().stream())
                .map(Language::getLanguage)
                .collect(Collectors.toCollection(TreeSet::new));
    }

    private Set<String> extractRatings(List<CinemaMovie> movies) {
        return movies.stream()
                .map(cm -> cm.getMovie().getRated())
                .collect(Collectors.toCollection(TreeSet::new));
    }

    private Set<String> extractGenres(List<CinemaMovie> movies) {
        return movies.stream()
                .flatMap(cm -> cm.getMovie().getGenre().stream())
                .map(Genre::getGenre)
                .collect(Collectors.toCollection(TreeSet::new));
    }

    private boolean movieContainsAnyLanguage(CinemaMovie movie, List<String> languages) {
        System.err.println("MOVIE: "+movie.getMovie().getLanguage());
        System.err.println("LANGUAGES: "+languages);
        return movie.getMovie().getLanguage().stream()
                .anyMatch(language -> languages.contains(language.getLanguage()));
    }

    private boolean movieContainsAnyGenre(CinemaMovie movie, List<String> genres) {
        return movie.getMovie().getGenre().stream()
                .anyMatch(genre -> genres.contains(genre.getGenre()));
    }


}
