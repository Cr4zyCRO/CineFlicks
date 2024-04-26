package org.bg121788.cineflicks.service.movie_related;

import lombok.AllArgsConstructor;
import org.bg121788.cineflicks.entity.Writer;
import org.bg121788.cineflicks.repository.WriterRepository;
import org.bg121788.cineflicks.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class WriterService {
    private final WriterRepository writerRepository;

    public List<Writer> getFromDTO(List<String> names) {
        return EntityUtils.getEntitiesFromDTO(names, this::getOrCreate);
    }

    private Writer getOrCreate(String name) {
        return EntityUtils.getOrCreateEntity(
                writerRepository,
                value -> value.getWriter().equals(name),
                v -> {
                    Writer newValue = new Writer();
                    newValue.setWriter(name);
                    return writerRepository.save(newValue);
                }
        );
    }
}
