package org.bg121788.cineflicks.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "movie", schema = "public")
public class Movie {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "title")
    private String title;

    @Column(name = "year")
    private Integer year;

    @Column(name = "rated")
    private String rated;

    @Column(name = "runtime")
    private String runtime;

    @Column(name = "plot")
    private String plot;

    @Column(name = "country")
    private String country;

    @Column(name = "awards")
    private String awards;

    @Column(name = "poster")
    private String poster;

    @Column(name = "metascore")
    private Integer metascore;

    @Column(name = "imdb_rating")
    private Double imdb_rating;

    @Column(name = "imdb_votes")
    private Integer imdb_votes;

    @Column(name = "imdb_id")
    private String imdb_id;

    @Column(name = "movie_views")
    private Integer movie_views;


    @Column(name = "language")
    @ManyToMany
    private List<Language> language;

    @Column(name = "genre")
    @ManyToMany
    private List<Genre> genre;

    @Column(name = "director")
    @ManyToMany
    private List<Director> director;

    @Column(name = "writer")
    @ManyToMany
    private List<Writer> writer;

    @Column(name = "actor")
    @ManyToMany
    private List<Actor> actor;


    @OneToMany(mappedBy = "movie")
    private List<CinemaMovie> cinemaMovies;

}
