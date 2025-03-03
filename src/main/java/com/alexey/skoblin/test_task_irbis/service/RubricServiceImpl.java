package com.alexey.skoblin.test_task_irbis.service;

import com.alexey.skoblin.test_task_irbis.dto.NewsDto;
import com.alexey.skoblin.test_task_irbis.dto.ResourceDto;
import com.alexey.skoblin.test_task_irbis.dto.RubricDto;
import com.alexey.skoblin.test_task_irbis.entity.News;
import com.alexey.skoblin.test_task_irbis.entity.Resource;
import com.alexey.skoblin.test_task_irbis.entity.Rubric;
import com.alexey.skoblin.test_task_irbis.exception.EntityNotFoundByIdException;
import com.alexey.skoblin.test_task_irbis.mapper.BaseMapper;
import com.alexey.skoblin.test_task_irbis.mapper.NewsMapper;
import com.alexey.skoblin.test_task_irbis.mapper.ResourceMapper;
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

    RubricMapper rubricMapper;
    RubricRepository rubricRepository;
    private NewsMapper newsMapper;
    private NewsService newsService;

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
        rubric.setUrl(dto.getUrl());
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

    @Override
    public void saveAll(List<RubricDto> dtos) {
        List<Rubric> rubrics = rubricMapper.toEntityList(dtos);
        rubricRepository.saveAll(rubrics);
    }

    @Override
    public Optional<UUID> findByNameAndResourceId(String name, UUID resourceId) {
        Rubric rubric = rubricRepository.findByNameAndResource_Id(name, resourceId);
        return rubric != null ? Optional.of(rubric.getId()) : Optional.empty();
    }

    @Override
    public void saveAllNewsWithRubric(RubricDto rubricDto, List<NewsDto> newsDtos) {
        Rubric rubric = rubricMapper.toEntity(rubricDto);
        List<News> news = newsMapper.toEntityList(newsDtos);
        for (News newsEntity : news) {
            rubric.getNews().add(newsEntity);
            newsEntity.setRubric(rubric);
        }
        rubricRepository.save(rubric);
        newsService.saveAll(newsMapper.toDtoList(news));
    }
}
