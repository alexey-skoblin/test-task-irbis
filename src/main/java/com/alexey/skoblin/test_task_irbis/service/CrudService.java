package com.alexey.skoblin.test_task_irbis.service;

import java.util.List;

public interface CrudService<D, ID> {

    D findById(ID id);

    D create(D dto);

    D update(ID id, D dto);

    D delete(ID id);

}
