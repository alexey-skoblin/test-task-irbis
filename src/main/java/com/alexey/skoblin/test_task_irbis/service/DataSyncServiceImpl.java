package com.alexey.skoblin.test_task_irbis.service;

import com.alexey.skoblin.test_task_irbis.dto.ResourceDto;
import com.alexey.skoblin.test_task_irbis.entity.News;
import com.alexey.skoblin.test_task_irbis.entity.Resource;
import com.alexey.skoblin.test_task_irbis.mapper.ResourceMapper;
import com.alexey.skoblin.test_task_irbis.repository.NewsRepository;
import com.alexey.skoblin.test_task_irbis.repository.ResourceRepository;
import com.alexey.skoblin.test_task_irbis.repository.RubricRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class DataSyncServiceImpl implements DataSyncService {

    private final ResourceMapper resourceMapper;
    private final RubricRepository rubricRepository;
    private final NewsRepository newsRepository;
    private final ResourceRepository resourceRepository;

    @Override
    public ResourceDto updateWithSubEntities(ResourceDto resourceDto) {
        Resource resource = resourceMapper.toEntity(resourceDto);
        Map<String, News> seenNews = new HashMap<>();

        resource.getRubrics().forEach(rubric -> {
            rubric.setResource(resource);
            rubric.setId(rubricRepository
                    .findIdByUrlAndResourceId(rubric.getUrl(), resource.getId()).orElse(null));

            List<News> dedupedNews = new ArrayList<>();
            for (News news : rubric.getNews()) {
                String url = news.getUrl();
                if (seenNews.containsKey(url)) {
                    News existingNews = seenNews.get(url);
                    existingNews.getRubrics().add(rubric);
                    dedupedNews.add(existingNews);
                } else {
                    news.setId(newsRepository.findIdByUrl(url).orElse(null));
                    news.getRubrics().add(rubric);
                    seenNews.put(url, news);
                    dedupedNews.add(news);
                }
            }
            rubric.setNews(dedupedNews);
        });

        return resourceMapper.toDto(resourceRepository.save(resource));
    }


}
