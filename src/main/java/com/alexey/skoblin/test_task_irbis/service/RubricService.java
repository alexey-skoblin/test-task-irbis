package com.alexey.skoblin.test_task_irbis.service;

import com.alexey.skoblin.test_task_irbis.dto.NewsDto;
import com.alexey.skoblin.test_task_irbis.dto.RubricDto;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RubricService {

    RubricDto findById(UUID uuid);

    RubricDto create(RubricDto dto);

    RubricDto update(UUID uuid, RubricDto dto);

    RubricDto delete(UUID uuid);

    Optional<UUID> findIdByUrlAndResourceId(UUID resourceId, String name);
}
