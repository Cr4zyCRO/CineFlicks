package org.bg121788.cineflicks.dto;

import lombok.Data;
import org.bg121788.cineflicks.entity.Cinema;
import org.bg121788.cineflicks.entity.Movie;

import java.time.LocalDateTime;

@Data
public class CinemaMovieDTO {
    private Cinema cinema;
    private Movie movie;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Double seatPrice;

}
