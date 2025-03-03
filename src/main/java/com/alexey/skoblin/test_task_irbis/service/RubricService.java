package com.alexey.skoblin.test_task_irbis.service;

import com.alexey.skoblin.test_task_irbis.dto.NewsDto;
import com.alexey.skoblin.test_task_irbis.dto.ResourceDto;
import com.alexey.skoblin.test_task_irbis.dto.RubricDto;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RubricService extends CrudService<RubricDto, UUID> {

    void saveAll(List<RubricDto> dtos);

    Optional<UUID> findByNameAndResourceId(String name, UUID resourceId);

    void saveAllNewsWithRubric(RubricDto rubric, List<NewsDto> news);
}
