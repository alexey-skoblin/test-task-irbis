package com.alexey.skoblin.test_task_irbis.mapper;

import java.util.List;

public interface BaseMapper<D, E> {

    E toEntity(D dto);

    D toDto(E entity);

    List<D> toDtoList(List<E> entities);

    List<E> toEntityList(List<D> dtos);

}
