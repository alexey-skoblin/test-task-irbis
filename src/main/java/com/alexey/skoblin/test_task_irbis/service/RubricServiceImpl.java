package com.alexey.skoblin.test_task_irbis.service;

import com.alexey.skoblin.test_task_irbis.dto.RubricDto;
import com.alexey.skoblin.test_task_irbis.entity.Rubric;
import com.alexey.skoblin.test_task_irbis.exception.EntityNotFoundByIdException;
import com.alexey.skoblin.test_task_irbis.mapper.RubricMapper;
import com.alexey.skoblin.test_task_irbis.repository.RubricRepository;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@AllArgsConstructor
public class RubricServiceImpl implements RubricService {

    RubricMapper rubricMapper;
    RubricRepository rubricRepository;

    @Override
    public List<RubricDto> findAll() {
        return rubricMapper.toDtoList(rubricRepository.findAll());
    }

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
        rubric.setName(dto.getName());
        rubric = rubricRepository.save(rubric);
        return rubricMapper.toDto(rubric);
    }

    @Override
    public void delete(UUID uuid) {
        if (!rubricRepository.existsById(uuid)) {
            throw new EntityNotFoundByIdException(Rubric.class, uuid.toString());
        }
        rubricRepository.deleteById(uuid);
    }
}
