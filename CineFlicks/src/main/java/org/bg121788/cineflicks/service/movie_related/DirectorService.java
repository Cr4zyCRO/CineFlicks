package org.bg121788.cineflicks.service.movie_related;

import lombok.AllArgsConstructor;
import org.bg121788.cineflicks.entity.Director;
import org.bg121788.cineflicks.repository.DirectorRepository;
import org.bg121788.cineflicks.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class DirectorService {
    private final DirectorRepository directorRepository;
    public List<Director> getFromDTO(List<String> names) {
        return EntityUtils.getEntitiesFromDTO(names, this::getOrCreate);
    }

    private Director getOrCreate(String name) {
        return EntityUtils.getOrCreateEntity(
                directorRepository,
                value -> value.getDirector().equals(name),
                v -> {
                    Director newVal = new Director();
                    newVal.setDirector(name);
                    return directorRepository.save(newVal);
                }
        );
    }
}
