package com.alexey.skoblin.test_task_irbis.controller;

import com.alexey.skoblin.test_task_irbis.dto.ResourceDto;
import com.alexey.skoblin.test_task_irbis.dto.request.ResourceWebDto;
import com.alexey.skoblin.test_task_irbis.mapper.ResourceMapper;
import com.alexey.skoblin.test_task_irbis.service.ResourceService;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/rest/admin-ui/resources")
@RequiredArgsConstructor
public class ResourceController {

    private final ResourceService resourceService;

    @GetMapping
    public PagedModel<ResourceWebDto> getAll(@ParameterObject Pageable pageable) {
        Page<ResourceWebDto> resources = resourceService.getAll(pageable);
        return new PagedModel<>(resources);
    }

    @GetMapping("/{id}")
    public ResourceWebDto getOne(@PathVariable UUID id) {
        return resourceService.getById(id);
    }

    @PostMapping
    public ResourceWebDto create(@RequestBody ResourceWebDto resource) {
        return resourceService.create(resource);
    }

    @DeleteMapping("/{id}")
    public ResourceWebDto delete(@PathVariable UUID id) {
        return resourceService.delete(id);
    }

}
