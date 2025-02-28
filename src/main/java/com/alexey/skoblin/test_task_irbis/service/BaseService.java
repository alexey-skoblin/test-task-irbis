package com.alexey.skoblin.test_task_irbis.service;

import com.alexey.skoblin.test_task_irbis.dto.WebLinkDto;

import java.util.List;
import java.util.UUID;

public interface BaseService<D, ID> {

    List<D> findAll();

    D findById(ID id);

    D create(D dto);

    D update(ID id, D dto);

    void delete(ID id);

}
