package org.bg121788.cineflicks.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
public class Announcement {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "movie_id")
    private UUID movie_id;

    @Column(name = "expiration_date")
    private LocalDateTime expiration_date;
}
