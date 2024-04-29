package org.bg121788.cineflicks.service;

import lombok.AllArgsConstructor;
import org.bg121788.cineflicks.entity.Cinema;
import org.bg121788.cineflicks.entity.CinemaMovie;
import org.bg121788.cineflicks.repository.CinemaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Service
public class CinemaService {
    private final CinemaRepository cinemaRepository;
    private final CinemaMovieService cinemaMovieService;

    public List<Cinema> getAllCinemas() {
        return cinemaRepository.findAll();
    }

    public Optional<Cinema> getById(UUID cinemaId) {
        return cinemaRepository.findById(cinemaId);
    }

    public boolean isTimeSlotAvailable(Cinema cinema, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        List<CinemaMovie> existingMovies = cinemaMovieService.getCinemaMoviesByCinema(cinema);


        for (CinemaMovie existingMovie : existingMovies) {
            LocalDateTime existingStart = existingMovie.getStartTime();
            LocalDateTime existingEnd = existingMovie.getEndTime();

            // If the new movie's start or end time falls within the existing movie's slot, there's a conflict
            boolean startConflict = startDateTime.isAfter(existingStart) && startDateTime.isBefore(existingEnd);
            boolean endConflict = endDateTime.isAfter(existingStart) && endDateTime.isBefore(existingEnd);

            // If the new movie completely envelops the existing movie's slot, or vice versa
            boolean startEqual = startDateTime.isEqual(existingStart);
            boolean endEqual = endDateTime.isEqual(existingEnd);
            boolean envelopsExisting = startDateTime.isBefore(existingStart) && endDateTime.isAfter(existingEnd);
            boolean envelopedByExisting = existingStart.isBefore(startDateTime) && existingEnd.isAfter(endDateTime);

            if (startConflict || endConflict || startEqual || endEqual || envelopsExisting || envelopedByExisting) {
                return false; // There is a conflict
            }
        }
        return true;

    }
}
