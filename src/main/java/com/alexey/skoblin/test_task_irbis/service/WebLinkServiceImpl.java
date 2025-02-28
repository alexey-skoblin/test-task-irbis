package com.alexey.skoblin.test_task_irbis.service;

import com.alexey.skoblin.test_task_irbis.dto.WebLinkDto;
import com.alexey.skoblin.test_task_irbis.entity.WebLink;
import com.alexey.skoblin.test_task_irbis.repository.WebLinkRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class WebLinkServiceImpl implements WebLinkService {

    private WebLinkRepository webLinkRepository;
    private WebLinkMapper webLinkMapper;

    @Override
    public List<WebLinkDto> findAll() {
       return webLinkRepository.findAll().stream().map(webLinkMapper::toDto).toList();
    }

    @Override
    public WebLinkDto findById(UUID id) {
        return null;
    }

    @Override
    public WebLinkDto create(WebLinkDto linkDto) {
        return null;
    }

    @Override
    public WebLinkDto update(UUID id, WebLinkDto linkDto) {
        return null;
    }

    @Override
    public void delete(UUID id) {

    }
}
