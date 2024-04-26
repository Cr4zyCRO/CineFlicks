package org.bg121788.cineflicks.service.movie_related;

import lombok.AllArgsConstructor;
import org.bg121788.cineflicks.entity.Actor;
import org.bg121788.cineflicks.repository.ActorRepository;
import org.bg121788.cineflicks.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class ActorService {
    private final ActorRepository actorRepository;
    public List<Actor> getFromDTO(List<String> names) {
        return EntityUtils.getEntitiesFromDTO(names, this::getOrCreate);
    }

    private Actor getOrCreate(String name) {
        return EntityUtils.getOrCreateEntity(
                actorRepository,
                value -> value.getActor().equals(name),
                v -> {
                    Actor newValue = new Actor();
                    newValue.setActor(name);
                    return actorRepository.save(newValue);
                }
        );
    }
}
