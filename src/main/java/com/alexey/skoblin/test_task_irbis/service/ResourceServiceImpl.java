package com.alexey.skoblin.test_task_irbis.service;

import com.alexey.skoblin.test_task_irbis.dto.ResourceDto;
import com.alexey.skoblin.test_task_irbis.entity.Resource;
import com.alexey.skoblin.test_task_irbis.exception.EntityNotFoundByIdException;
import com.alexey.skoblin.test_task_irbis.mapper.ResourceMapper;
import com.alexey.skoblin.test_task_irbis.repository.ResourceRepository;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@AllArgsConstructor
public class ResourceServiceImpl implements ResourceService {

    ResourceMapper resourceMapper;
    ResourceRepository resourceRepository;

    @Override
    public List<ResourceDto> findAll() {
        return resourceMapper.toDtoList(resourceRepository.findAll());
    }

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
        resource.setName(dto.getName());
        resource.setUrl(dto.getUrl());
        resource = resourceRepository.save(resource);
        return resourceMapper.toDto(resource);
    }

    @Override
    public void delete(UUID uuid) {
        if (!resourceRepository.existsById(uuid)) {
            throw new EntityNotFoundByIdException(Resource.class, uuid.toString());
        }
        resourceRepository.deleteById(uuid);
    }
}
