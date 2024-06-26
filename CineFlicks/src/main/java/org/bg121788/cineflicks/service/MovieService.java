package org.bg121788.cineflicks.service;

import lombok.AllArgsConstructor;
import org.bg121788.cineflicks.dto.MovieDTO;
import org.bg121788.cineflicks.entity.Movie;
import org.bg121788.cineflicks.repository.CinemaMovieRepository;
import org.bg121788.cineflicks.repository.MovieRepository;
import org.bg121788.cineflicks.service.movie_related.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Service
public class MovieService {
    private final MovieRepository movieRepository;

    private final GenreService genreService;
    private final DirectorService directorService;
    private final WriterService writerService;
    private final ActorService actorService;
    private final LanguageService languageService;

    private final CinemaMovieRepository cinemaMovieRepository;

    public boolean addNewMovie(MovieDTO movieDTO){

        if (movieRepository.existsByTitle(movieDTO.getTitle())) {
            return false;
        }

        // Create a new Movie entity from the MovieDTO
        Movie movie = new Movie();

        movie.setTitle(movieDTO.getTitle());
        movie.setYear(movieDTO.getYear());
        movie.setRated(movieDTO.getRated());
        movie.setRuntime(movieDTO.getRuntime());
        movie.setPlot(movieDTO.getPlot());
        movie.setCountry(movieDTO.getCountry());
        movie.setAwards(movieDTO.getAwards());
        movie.setPoster(movieDTO.getPoster());
        movie.setMetascore(movieDTO.getMetascore());
        movie.setImdb_rating(movieDTO.getImdb_rating());
        movie.setImdb_votes(movieDTO.getImdb_votes());
        movie.setImdb_id(movieDTO.getImdb_id());
        movie.setMovie_views(0);

        movie.setGenre(genreService.getFromDTO(movieDTO.getGenre()));
        movie.setDirector(directorService.getFromDTO(movieDTO.getDirector()));
        movie.setWriter(writerService.getFromDTO(movieDTO.getWriter()));
        movie.setActor(actorService.getFromDTO(movieDTO.getActor()));
        movie.setLanguage(languageService.getFromDTO(movieDTO.getLanguage()));


        movieRepository.save(movie);

        return true; // Movie successfully added
    }

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public Optional<Movie> getById(UUID movieId) {
        return movieRepository.findById(movieId);
    }

    public List<Movie> getTop10MostRated(){
        return movieRepository.findTop10ByOrderByImdb_ratingDesc();
    }
    public List<Movie> getTop10MostViewed(){
        return movieRepository.findTop10ByOrderByImdb_votesDesc();
    }

    public List<Movie> searchMovies(String query) {
        return movieRepository.findByTitleContainingIgnoreCase(query);
    }


    public void deleteMovie(UUID movieId) {
        movieRepository.deleteById(movieId);
    }

    public List<Movie> getAssignedMovies() {
        List<Movie> assignedMovies = new ArrayList<>();
        List<Movie> allMovies = getAllMovies();
        for (Movie movie : allMovies) {
            if (cinemaMovieRepository.existsByMovie(movie)) {
                assignedMovies.add(movie);
            }
        }
        return assignedMovies;
    }

    public List<Movie> getNonAssignedMovies() {
        List<Movie> nonAssignedMovies = new ArrayList<>();
        List<Movie> allMovies = getAllMovies();
        for (Movie movie : allMovies) {
            if (!cinemaMovieRepository.existsByMovie(movie)) {
                nonAssignedMovies.add(movie);
            }
        }
        return nonAssignedMovies;
    }

    public void updateViews(Movie movie, int size) {
        movie.setMovie_views(movie.getMovie_views() + size);
        movieRepository.save(movie);
    }

    public void updateRating(Movie movie, Integer rating) {
        Double totalScore = movie.getImdb_rating() * movie.getImdb_votes();
        totalScore += rating;
        movie.setImdb_votes(movie.getImdb_votes()+1);
        movie.setImdb_rating(totalScore / movie.getImdb_votes());
        movieRepository.save(movie);
    }


}
