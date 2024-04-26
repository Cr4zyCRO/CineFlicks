package org.bg121788.cineflicks.service;

import lombok.AllArgsConstructor;
import org.bg121788.cineflicks.entity.CinemaMovie;
import org.bg121788.cineflicks.repository.CinemaMovieRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.util.Comparator;
import java.util.List;

@AllArgsConstructor
@Service
public class CinemaMovieService {
    private final CinemaMovieRepository cinemaMovieRepository;

    public List<CinemaMovie> getMoviesForThisWeek(String sortBy){
        LocalDateTime start = LocalDateTime.now().with(ChronoField.DAY_OF_WEEK, 1);
        LocalDateTime end = start.plusDays(7);

        List<CinemaMovie> movies = cinemaMovieRepository.findAllByStartTimeBetween(start,end);

        if (sortBy!=null){
            switch (sortBy){
                case "name":
                    movies.sort(Comparator.comparing(cm -> cm.getMovie().getTitle()));
                    break;
                case "startTime":
                    movies.sort(Comparator.comparing(CinemaMovie::getStartTime));
                    break;
            }
        }

        return movies;

    }
}
