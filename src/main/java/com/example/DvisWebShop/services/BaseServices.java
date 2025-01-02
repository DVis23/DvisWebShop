package com.example.DvisWebShop.services;

import jakarta.persistence.EntityNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class BaseServices {
    protected <T, R> List<R> buildResponseList(List<T> entities, Function<T, R> mapper) {
        return entities.stream()
                .map(mapper)
                .collect(Collectors.toList());
    }

    protected <T> T findEntityById(Optional<T> optionalEntity, String entityName, Integer id) {
        return optionalEntity.orElseThrow(() -> new EntityNotFoundException(entityName + " with id = '" + id + "' does not exist"));
    }
}
