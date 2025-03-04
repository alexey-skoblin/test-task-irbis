package com.alexey.skoblin.test_task_irbis.service;

import com.alexey.skoblin.test_task_irbis.dto.ResourceDto;
import com.alexey.skoblin.test_task_irbis.dto.RubricDto;
import com.alexey.skoblin.test_task_irbis.entity.Resource;
import com.alexey.skoblin.test_task_irbis.entity.Rubric;
import com.alexey.skoblin.test_task_irbis.exception.EntityNotFoundByIdException;
import com.alexey.skoblin.test_task_irbis.exception.EntityNotFoundByNameException;
import com.alexey.skoblin.test_task_irbis.mapper.ResourceMapper;
import com.alexey.skoblin.test_task_irbis.mapper.RubricMapper;
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

    RubricService rubricService;

    ResourceMapper resourceMapper;
    ResourceRepository resourceRepository;
    private RubricMapper rubricMapper;

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
        ResourceDto resourceDto = resourceMapper.toDto(resource);
        return resourceDto;
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

    @Override
    public ResourceDto findByName(String name) {
        Resource resource = resourceRepository.findByName(name);
        if (resource == null) {
            throw new EntityNotFoundByNameException(Resource.class, name);
        }
        return resourceMapper.toDto(resource);
    }

    @Override
    public List<RubricDto> saveAllRubricWithResource(List<RubricDto> dtos, ResourceDto resourceDto) {
        Resource resource = resourceMapper.toEntity(resourceDto);
        List<Rubric> rubrics = rubricMapper.toEntityList(dtos);
        for (Rubric rubric : rubrics) {
            if (rubric.getId() != null) {
              continue;
            }
            resource.getRubrics().add(rubric);
            rubric.setResource(resource);
        }
        resourceRepository.save(resource);
        return rubricMapper.toDtoList(rubrics);
//        rubricService.saveAll(rubricMapper.toDtoList(rubrics));
    }
}
