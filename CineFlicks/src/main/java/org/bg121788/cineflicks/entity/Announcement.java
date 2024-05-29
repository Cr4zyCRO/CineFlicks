package org.bg121788.cineflicks.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "announcement", schema = "public")
public class Announcement {
    @Id
    @GeneratedValue
    private UUID id;


    @ManyToOne
    @JoinColumn(name = "movie_id", referencedColumnName = "id")
    private Movie movie;

    @Column(name = "expiration_date")
    private LocalDateTime expiration_date;
}
