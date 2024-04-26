package org.bg121788.cineflicks.dto;

import lombok.Data;

@Data
public class TicketDTO {
    private String userId;
    private String cinemaId;
    private String movieId;
    private Double price;
    private String selectedSeats;
}
