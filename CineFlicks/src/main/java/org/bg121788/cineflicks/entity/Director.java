package org.bg121788.cineflicks.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.UUID;
@Data
@Entity
public class Director {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "director")
    private String director;
}
