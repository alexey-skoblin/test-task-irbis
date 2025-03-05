package com.alexey.skoblin.test_task_irbis.service;

import com.alexey.skoblin.test_task_irbis.dto.ResourceDto;
import com.alexey.skoblin.test_task_irbis.entity.Resource;
import com.alexey.skoblin.test_task_irbis.mapper.ResourceMapper;
import com.alexey.skoblin.test_task_irbis.repository.NewsRepository;
import com.alexey.skoblin.test_task_irbis.repository.ResourceRepository;
import com.alexey.skoblin.test_task_irbis.repository.RubricRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DataSyncServiceImpl implements DataSyncService {

    private final ResourceMapper resourceMapper;
    private final RubricRepository rubricRepository;
    private final NewsRepository newsRepository;
    private final ResourceRepository resourceRepository;

    @Override
    public ResourceDto updateWithSubEntities(ResourceDto resourceDto){
        Resource resource = resourceMapper.toEntity(resourceDto);
        resource.getRubrics().forEach(rubric -> {
            rubric.setResource(resource);
            rubric.setId(rubricRepository
                    .findIdByUrlAndResourceId(rubric.getUrl(), resource.getId()).orElse(null));
            rubric.getNews().forEach(news -> {
                news.setRubric(rubric);
                news.setId(newsRepository
                        .findIdByUrl(news.getUrl()).orElse(null));
            });
        });
        return resourceMapper.toDto(resourceRepository.save(resource));
    }

}
