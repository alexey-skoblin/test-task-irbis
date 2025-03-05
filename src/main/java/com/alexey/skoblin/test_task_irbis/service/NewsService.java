package com.alexey.skoblin.test_task_irbis.service;

import com.alexey.skoblin.test_task_irbis.dto.NewsDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface NewsService extends CrudService<NewsDto, UUID> {


    Optional<UUID> findByUrl(String url);

    void saveAll(List<NewsDto> news);
}
