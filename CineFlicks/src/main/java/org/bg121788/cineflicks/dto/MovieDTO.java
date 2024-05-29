package org.bg121788.cineflicks.dto;

import lombok.Data;
import java.util.List;

@Data
public class MovieDTO {
    private String title;
    private Integer year;
    private String rated;
    private String runtime;
    private String plot;
    private String country;
    private String awards;
    private String poster;
    private Integer metascore;
    private Double imdb_rating;
    private Integer imdb_votes;
    private String imdb_id;
    private List<String> genre;
    private List<String> director;
    private List<String> writer;
    private List<String> actor;
    private List<String> language;
    private Integer movie_views;
}
