package com.alexey.skoblin.test_task_irbis.service;

import com.alexey.skoblin.test_task_irbis.dto.NewsDto;
import com.alexey.skoblin.test_task_irbis.entity.News;
import com.alexey.skoblin.test_task_irbis.entity.Rubric;
import com.alexey.skoblin.test_task_irbis.exception.EntityNotFoundByIdException;
import com.alexey.skoblin.test_task_irbis.mapper.NewsMapper;
import com.alexey.skoblin.test_task_irbis.mapper.RubricMapper;
import com.alexey.skoblin.test_task_irbis.repository.NewsRepository;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@AllArgsConstructor
public class NewsServiceImpl implements NewsService {

    private final NewsMapper newsMapper;
    private final NewsRepository newsRepository;
    private RubricMapper rubricMapper;

    @Override
    public List<NewsDto> findAll() {
        return newsMapper.toDtoList(newsRepository.findAll());
    }

    @Override
    public NewsDto findById(UUID uuid) {
        News news = newsRepository.findById(uuid)
            .orElseThrow(() -> new EntityNotFoundByIdException(News.class, uuid.toString()));
        return newsMapper.toDto(news);
    }

    @Override
    public NewsDto create(NewsDto dto) {
        News news = newsMapper.toEntity(dto);
        news = newsRepository.save(news);
        return newsMapper.toDto(news);
    }

    @Override
    public NewsDto update(UUID uuid, NewsDto dto) {
        News news = newsRepository.findById(uuid)
            .orElseThrow(() -> new EntityNotFoundByIdException(News.class, uuid.toString()));
        news.setTitle(dto.getTitle());
        news.setUrl(dto.getUrl());
        news.setDateTime(dto.getDateTime());
        news = newsRepository.save(news);
        return newsMapper.toDto(news);
    }

    @Override
    public void delete(UUID uuid) {
        if (!newsRepository.existsById(uuid)) {
            throw new EntityNotFoundByIdException(News.class, uuid.toString());
        }
        newsRepository.deleteById(uuid);

    }

    @Override
    public void saveAll(List<NewsDto> news) {
        List<News> entities = newsMapper.toEntityList(news);
        newsRepository.saveAll(entities);
    }
}
