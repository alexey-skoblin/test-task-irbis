package com.alexey.skoblin.test_task_irbis.service;

import com.alexey.skoblin.test_task_irbis.dto.NewsDto;

import java.util.List;
import java.util.UUID;

public interface NewsService extends CrudService<NewsDto, UUID> {

    void saveAll(List<NewsDto> news);
}
