package org.bg121788.cineflicks.repository;

import org.bg121788.cineflicks.entity.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LanguageRepository extends JpaRepository<Language, UUID> {
    Language findByLanguage(String language);
}
