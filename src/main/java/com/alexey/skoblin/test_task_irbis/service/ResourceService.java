package com.alexey.skoblin.test_task_irbis.service;

import com.alexey.skoblin.test_task_irbis.dto.ResourceDto;
import com.alexey.skoblin.test_task_irbis.dto.RubricDto;

import java.util.List;
import java.util.UUID;

public interface ResourceService extends CrudService<ResourceDto, UUID> {

    ResourceDto findByName(String name);

    List<RubricDto> saveAllRubricWithResource(List<RubricDto> dtos, ResourceDto resourceDto);
}
