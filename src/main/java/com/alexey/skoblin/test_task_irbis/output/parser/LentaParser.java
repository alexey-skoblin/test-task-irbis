package com.alexey.skoblin.test_task_irbis.output.parser;

import com.alexey.skoblin.test_task_irbis.dto.NewsDto;
import com.alexey.skoblin.test_task_irbis.dto.ResourceDto;
import com.alexey.skoblin.test_task_irbis.dto.RubricDto;
import com.alexey.skoblin.test_task_irbis.mapper.ResourceMapper;
import com.alexey.skoblin.test_task_irbis.mapper.RubricMapper;
import com.alexey.skoblin.test_task_irbis.service.NewsService;
import com.alexey.skoblin.test_task_irbis.service.ResourceService;
import com.alexey.skoblin.test_task_irbis.service.RubricService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LentaParser {

    private static final String NAME = "Лента";

    private static final Logger log = LoggerFactory.getLogger(LentaParser.class);
    private final ResourceService resourceService;
    private final RubricService rubricService;
    private final NewsService newsService;

    private final RubricMapper rubricMapper;
    private final ResourceMapper resourceMapper;

    public void parse() {
        ResourceDto resource = resourceService.findByName(NAME);
        try {
            Document doc = Jsoup.connect(resource.getUrl()).get();
            log.atInfo().log("Parsing {} started", NAME);

            Elements rubricItems = doc.select(".menu__nav-list > li.menu__nav-item:has(a.menu__nav-link._is-extra)");
            List<RubricDto> rubrics = new ArrayList<>();
            for (Element item : rubricItems) {
                Element link = item.selectFirst("a.menu__nav-link");
                if (link != null) {
                    String name = link.text();
                    String url = link.attr("href");
                    Optional<UUID> uuid = rubricService
                        .findByNameAndResourceId(name, resource.getId());
                    RubricDto rubric = RubricDto.builder()
                        .id(uuid.orElse(null))
                        .name(name)
                        .url(url)
                        .build();
                    rubrics.add(rubric);
                }
            }
            rubricService.saveAll(rubrics, resource);

            rubrics.forEach(rubric -> {
                try {
                    Document rubricDoc = Jsoup.connect(resource.getUrl() + rubric.getUrl()).get();
                    Elements newsItems = rubricDoc.select(".card-mini");
                    List<NewsDto> news = new ArrayList<>();
                    for (Element item : newsItems) {
                        Element card = item.selectFirst(".card-mini__text");
                        if (card != null) {
                            // Извлекаем заголовок из тега h3
                            Element titleElement = card.selectFirst(".card-mini__title");
                            String title = titleElement != null ? titleElement.text() : "";

                            // Предположим, что ссылка находится в родительском элементе <a>
                            Element link = card.parents().select("a[href]").first();
                            String url = link != null ? link.attr("abs:href") : "";

                            news.add(NewsDto.builder()
                                .title(title)
                                .url(url)
                                .build());
                        }
                    }
                    newsService.saveAll(news, rubric);


                } catch (IOException er) {
                    log.atWarn()
                        .log("Parsing news for rubric: {} in resource: {} HttpStatusException: {}", NAME, rubric.getName(), er.getMessage());
                }
            });
        } catch (IOException e) {
            log.atWarn().log("Parsing resource {} IOException: {}", NAME, e.getMessage());
        }
    }


}
