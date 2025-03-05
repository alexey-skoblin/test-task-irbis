package com.alexey.skoblin.test_task_irbis.service;

import com.alexey.skoblin.test_task_irbis.dto.NewsDto;
import com.alexey.skoblin.test_task_irbis.dto.RubricDto;
import com.alexey.skoblin.test_task_irbis.entity.News;
import com.alexey.skoblin.test_task_irbis.entity.Rubric;
import com.alexey.skoblin.test_task_irbis.exception.EntityNotFoundByIdException;
import com.alexey.skoblin.test_task_irbis.mapper.NewsMapper;
import com.alexey.skoblin.test_task_irbis.mapper.RubricMapper;
import com.alexey.skoblin.test_task_irbis.repository.RubricRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@AllArgsConstructor
public class RubricServiceImpl implements RubricService {

    private RubricMapper rubricMapper;
    private RubricRepository rubricRepository;

    @Override
    public RubricDto findById(UUID uuid) {
        Rubric rubric = rubricRepository.findById(uuid)
            .orElseThrow(() -> new EntityNotFoundByIdException(Rubric.class, uuid.toString()));
        return rubricMapper.toDto(rubric);
    }

    @Override
    public RubricDto create(RubricDto dto) {
        Rubric rubric = rubricMapper.toEntity(dto);
        rubric = rubricRepository.save(rubric);
        return rubricMapper.toDto(rubric);
    }

    @Override
    public RubricDto update(UUID uuid, RubricDto dto) {
        Rubric rubric = rubricRepository.findById(uuid)
            .orElseThrow(() -> new EntityNotFoundByIdException(Rubric.class, uuid.toString()));
        rubric.setName(dto.name());
        rubric.setUrl(dto.url());
        rubric = rubricRepository.save(rubric);
        return rubricMapper.toDto(rubric);
    }

    @Override
    public RubricDto delete(UUID uuid) {
        Rubric rubric = rubricRepository.findById(uuid)
            .orElseThrow(() -> new EntityNotFoundByIdException(Rubric.class, uuid.toString()));
        rubricRepository.deleteById(uuid);
        return rubricMapper.toDto(rubric);
    }

    @Override
    public Optional<UUID> findIdByUrlAndResourceId(UUID resourceId, String name) {
        return rubricRepository.findIdByUrlAndResourceId(name, resourceId);
    }

}
