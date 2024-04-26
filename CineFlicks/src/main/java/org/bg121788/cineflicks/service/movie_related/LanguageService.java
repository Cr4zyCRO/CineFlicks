package org.bg121788.cineflicks.service.movie_related;

import lombok.AllArgsConstructor;
import org.bg121788.cineflicks.entity.Language;
import org.bg121788.cineflicks.repository.LanguageRepository;
import org.bg121788.cineflicks.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class LanguageService {
    private final LanguageRepository languageRepository;
    public List<Language> getFromDTO(List<String> names) {
        return EntityUtils.getEntitiesFromDTO(names, this::getOrCreate);
    }

    private Language getOrCreate(String name) {
        return EntityUtils.getOrCreateEntity(
                languageRepository,
                value -> value.getLanguage().equals(name),
                v -> {
                    Language newValue = new Language();
                    newValue.setLanguage(name);
                    return languageRepository.save(newValue);
                }
        );
    }
}
