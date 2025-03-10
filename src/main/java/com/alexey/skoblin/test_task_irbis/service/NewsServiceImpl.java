package com.alexey.skoblin.test_task_irbis.service;

import com.alexey.skoblin.test_task_irbis.dto.NewsDto;
import com.alexey.skoblin.test_task_irbis.entity.News;
import com.alexey.skoblin.test_task_irbis.exception.EntityNotFoundByIdException;
import com.alexey.skoblin.test_task_irbis.mapper.NewsMapper;
import com.alexey.skoblin.test_task_irbis.mapper.RubricMapper;
import com.alexey.skoblin.test_task_irbis.repository.NewsRepository;
import java.util.List;
import java.util.Optional;
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
        news.setTitle(dto.title());
        news.setUrl(dto.url());
        news.setDateTime(dto.dateTime());
        news = newsRepository.save(news);
        return newsMapper.toDto(news);
    }

    @Override
    public NewsDto delete(UUID uuid) {
        News news = newsRepository.findById(uuid)
            .orElseThrow(() -> new EntityNotFoundByIdException(News.class, uuid.toString()));
        newsRepository.deleteById(uuid);
        return newsMapper.toDto(news);
    }

    @Override
    public Optional<UUID> findByUrl(String url) {
        return newsRepository.findIdByUrl(url);
    }

}
