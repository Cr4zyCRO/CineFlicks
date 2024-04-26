package org.bg121788.cineflicks.service.movie_related;

import lombok.AllArgsConstructor;
import org.bg121788.cineflicks.entity.Genre;
import org.bg121788.cineflicks.repository.GenreRepository;
import org.bg121788.cineflicks.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class GenreService {
    private final GenreRepository genreRepository;
    public List<Genre> getFromDTO(List<String> names) {
        return EntityUtils.getEntitiesFromDTO(names, this::getOrCreate);
    }

    private Genre getOrCreate(String name) {
        return EntityUtils.getOrCreateEntity(
                genreRepository,
                value -> value.getGenre().equals(name),
                v -> {
                    Genre newVal = new Genre();
                    newVal.setGenre(name);
                    return genreRepository.save(newVal);
                }
        );
    }
}
