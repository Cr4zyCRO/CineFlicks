package org.bg121788.cineflicks.util;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class EntityUtils {
    public static <T, R> List<T> getEntitiesFromDTO(
            List<R> names,
            Function<R, T> getOrCreateFunction
    ) {
        return names.stream()
                .map(getOrCreateFunction)
                .collect(Collectors.toList());
    }

    public static <T> T getOrCreateEntity(
            JpaRepository<T, ?> repository,
            Function<T, Boolean> existsFunction,
            Function<Void, T> createFunction
    ) {
        T entity = repository.findAll().stream()
                .filter(existsFunction::apply)
                .findFirst()
                .orElse(null);

        if (entity == null) {
            return createFunction.apply(null);
        }

        return entity;
    }
}
