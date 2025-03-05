package com.alexey.skoblin.test_task_irbis.service;

import com.alexey.skoblin.test_task_irbis.dto.NewsDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface NewsService {

    NewsDto findById(UUID uuid);

    NewsDto create(NewsDto dto);

    NewsDto update(UUID uuid, NewsDto dto);

    NewsDto delete(UUID uuid);

    Optional<UUID> findByUrl(String url);
}
