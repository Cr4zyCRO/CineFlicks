package org.bg121788.cineflicks.dto;

import lombok.Data;
import org.bg121788.cineflicks.entity.CinemaMovie;
import org.bg121788.cineflicks.entity.User;

@Data
public class TicketDTO {
    private UserDTO user;
    private CinemaMovie cinemaMovie;
    private Double price;
    private String selectedSeats;
}
