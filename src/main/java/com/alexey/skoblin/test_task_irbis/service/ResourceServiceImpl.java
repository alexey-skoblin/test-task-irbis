package com.alexey.skoblin.test_task_irbis.service;

import com.alexey.skoblin.test_task_irbis.dto.ResourceDto;
import com.alexey.skoblin.test_task_irbis.dto.request.ResourceWebDto;
import com.alexey.skoblin.test_task_irbis.entity.Resource;
import com.alexey.skoblin.test_task_irbis.exception.EntityNotFoundByIdException;
import com.alexey.skoblin.test_task_irbis.exception.EntityNotFoundByNameException;
import com.alexey.skoblin.test_task_irbis.mapper.ResourceMapper;
import com.alexey.skoblin.test_task_irbis.repository.ResourceRepository;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Transactional
@Service
@AllArgsConstructor
public class ResourceServiceImpl implements ResourceService {

    private ResourceMapper resourceMapper;
    private ResourceRepository resourceRepository;

    private final ObjectMapper objectMapper;

    @Override
    public ResourceDto findById(UUID uuid) {
        Resource resource = resourceRepository.findById(uuid)
                .orElseThrow(() -> new EntityNotFoundByIdException(Resource.class, uuid.toString()));
        return resourceMapper.toDto(resource);
    }

    @Override
    public ResourceDto create(ResourceDto dto) {
        Resource resource = resourceMapper.toEntity(dto);
        resource = resourceRepository.save(resource);
        return resourceMapper.toDto(resource);
    }

    @Override
    public ResourceDto update(UUID uuid, ResourceDto dto) {
        Resource resource = resourceRepository.findById(uuid)
                .orElseThrow(() -> new EntityNotFoundByIdException(Resource.class, uuid.toString()));
        resource.setName(dto.name());
        resource.setUrl(dto.url());

        resource = resourceRepository.save(resource);
        return resourceMapper.toDto(resource);
    }

    @Override
    public ResourceWebDto delete(UUID uuid) {
        Resource resource = resourceRepository.findById(uuid)
                .orElseThrow(() -> new EntityNotFoundByIdException(Resource.class, uuid.toString()));
        resourceRepository.deleteById(uuid);
        return resourceMapper.toResourceWebDto(resource);
    }

    @Override
    public ResourceWebDto getById(UUID id) {
        Resource resource = resourceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundByIdException(Resource.class, id.toString()));
        return resourceMapper.toResourceWebDto(resource);
    }

    @Override
    public ResourceDto findByName(String name) {
        Resource resource = resourceRepository.findByName(name);
        if (resource == null) {
            throw new EntityNotFoundByNameException(Resource.class, name);
        }
        return resourceMapper.toDto(resource);
    }

    @Override
    public Page<ResourceWebDto> getAll(Pageable pageable) {
        Page<Resource> page = resourceRepository.findAll(pageable);
        return page.map(e -> resourceMapper.toResourceWebDto(e));
    }

    @Override
    public ResourceWebDto create(ResourceWebDto resource) {
        Resource entity = resourceMapper.toEntity(resource);
        entity = resourceRepository.save(entity);
        return resourceMapper.toResourceWebDto(entity);
    }

}
