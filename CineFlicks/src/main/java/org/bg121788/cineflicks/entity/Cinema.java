package org.bg121788.cineflicks.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Entity
public class Cinema {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "room")
    private String room;

    @Column(name = "row")
    private Integer row;

    @Column(name = "column")
    private Integer column;

    @OneToMany(mappedBy = "cinema")
    private List<CinemaMovie> cinemaMovies;



}
