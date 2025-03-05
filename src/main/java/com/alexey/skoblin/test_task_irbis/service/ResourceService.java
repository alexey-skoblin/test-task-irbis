package com.alexey.skoblin.test_task_irbis.service;

import com.alexey.skoblin.test_task_irbis.dto.ResourceDto;
import com.alexey.skoblin.test_task_irbis.dto.request.ResourceWebDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface ResourceService {

    ResourceDto findById(UUID uuid);

    ResourceDto create(ResourceDto dto);

    ResourceDto update(UUID uuid, ResourceDto dto);

    ResourceDto findByName(String name);

    Page<ResourceWebDto> getAll(Pageable pageable);

    ResourceWebDto create(ResourceWebDto resource);

    ResourceWebDto delete(UUID id);

    ResourceWebDto getById(UUID id);
}
