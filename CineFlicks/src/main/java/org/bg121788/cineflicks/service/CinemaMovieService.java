package org.bg121788.cineflicks.service;

import lombok.AllArgsConstructor;
import org.bg121788.cineflicks.dto.CinemaMovieDTO;
import org.bg121788.cineflicks.entity.Cinema;
import org.bg121788.cineflicks.entity.CinemaMovie;
import org.bg121788.cineflicks.entity.Movie;
import org.bg121788.cineflicks.repository.CinemaMovieRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Service
public class CinemaMovieService {
    private final CinemaMovieRepository cinemaMovieRepository;
    private final TicketService ticketService;

    public List<CinemaMovie> getMoviesForThisWeek(String sortDir){
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = start.plusDays(7);

        List<CinemaMovie> movies = cinemaMovieRepository.findAllByStartTimeBetween(start, end);

        // Sort movies by name in the specified direction
        if ("ASC".equals(sortDir)) {
            movies.sort(Comparator.comparing(cm -> cm.getMovie().getTitle()));
        } else {
            movies.sort(Comparator.comparing(cm -> cm.getMovie().getTitle(), Comparator.reverseOrder()));
        }

        return movies;

    }


    public void saveCinemaMovie(CinemaMovieDTO cinemaMovieDTO) {
        CinemaMovie cinemaMovie = new CinemaMovie();
        cinemaMovie.setCinema(cinemaMovieDTO.getCinema());
        cinemaMovie.setMovie(cinemaMovieDTO.getMovie());
        cinemaMovie.setStartTime(cinemaMovieDTO.getStartTime());
        cinemaMovie.setEndTime(cinemaMovieDTO.getEndTime());
        cinemaMovie.setSeatPrice(cinemaMovieDTO.getSeatPrice());
        cinemaMovieRepository.save(cinemaMovie);
    }

    public List<CinemaMovie> getCinemaMoviesByCinema(Cinema cinema) {
        return cinemaMovieRepository.findByCinema(cinema);
    }

    public List<CinemaMovie> getCinemaMovies() {
        LocalDateTime start = LocalDateTime.now();
        return cinemaMovieRepository.findAllByStartTimeBeforeNow(start);
    }

    public Optional<CinemaMovie> getById(UUID cinemaMovieId) {
        return cinemaMovieRepository.findById(cinemaMovieId);
    }

    public void deleteMovieInCinemaMovie(Optional<Movie> movie) {
        List<CinemaMovie> cinemaMovies;
        if (movie.isPresent()) {
            cinemaMovies = cinemaMovieRepository.findAllByMovie(movie.get());
            for (CinemaMovie cinemaMovie : cinemaMovies){
                ticketService.deleteMovieTickets(cinemaMovie);
            }
            cinemaMovieRepository.deleteAllByMovie(movie.get());
        }
    }
}
