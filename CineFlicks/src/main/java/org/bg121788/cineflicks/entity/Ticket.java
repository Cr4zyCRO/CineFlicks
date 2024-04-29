package org.bg121788.cineflicks.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
public class Ticket {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "cinema_movie_id")
    private CinemaMovie cinemaMovie;

    @Column(name = "price")
    private Double price;

    @Column(name = "selected_seats")
    private String selectedSeats;

}
