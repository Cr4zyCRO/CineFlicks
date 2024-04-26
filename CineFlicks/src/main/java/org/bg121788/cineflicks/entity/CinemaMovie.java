package org.bg121788.cineflicks.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
public class CinemaMovie {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    private Cinema cinema;

    @ManyToOne
    private Movie movie;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

}
