package com.alexey.skoblin.test_task_irbis.exception;

import jakarta.persistence.EntityNotFoundException;
import lombok.Getter;

@Getter
public class EntityNotFoundByIdException extends EntityNotFoundException {

    private final Class<?> entityClass;
    private final String id;

    public EntityNotFoundByIdException(Class<?> tClass, String id) {
        super("Could not find " + tClass.getSimpleName() + " with id: " + id);
        this.entityClass = tClass;
        this.id = id;
    }

}
