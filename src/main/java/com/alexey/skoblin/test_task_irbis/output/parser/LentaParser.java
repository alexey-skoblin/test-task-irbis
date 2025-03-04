package com.alexey.skoblin.test_task_irbis.output.parser;

import com.alexey.skoblin.test_task_irbis.dto.NewsDto;
import com.alexey.skoblin.test_task_irbis.dto.ResourceDto;
import com.alexey.skoblin.test_task_irbis.dto.RubricDto;
import com.alexey.skoblin.test_task_irbis.mapper.ResourceMapper;
import com.alexey.skoblin.test_task_irbis.service.ResourceService;
import com.alexey.skoblin.test_task_irbis.service.RubricService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;

import com.alexey.skoblin.test_task_irbis.time.DateTimeParser;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class LentaParser {

    private static final String NAME = "Лента";

    private final ResourceService resourceService;
    private final RubricService rubricService;

    private final DateTimeParser dateTimeParser;
    private final ResourceMapper resourceMapper;

    public void parse() {
        ResourceDto resource = resourceService.findByName(NAME);
        try {
            log.atInfo().log("Parsing {} started", NAME);
            Document doc = Jsoup.connect(resource.getUrl()).get();

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
            rubrics = resourceService.saveAllRubricWithResource(rubrics, resource);

            rubrics.forEach(rubric -> {
                try {
                    Document rubricDoc = Jsoup.connect(resource.getUrl() + rubric.getUrl()).get();
                    List<Element> newsItems = getNewsItems(rubricDoc);
                    List<NewsDto> news = new ArrayList<>();
                    for (Element item : newsItems) {
                        Element titleElement = item.selectFirst("[class*='title']");
                        if (titleElement == null) {
                            continue;
                        }
                        String title = titleElement.text();
                        String url = item.attr("href");
                        Element timeElement = item.selectFirst("time");
                        String timeString = timeElement != null ? timeElement.text() : "";
                        LocalDateTime dateTime;
                        try {
                            dateTime = dateTimeParser.parse(url, timeString);
                        } catch (DateTimeParseException eNewsDateTime) {
                            log.atWarn().log("Error parsing dateTime for news: " + eNewsDateTime);
                            continue;
                        }
                        news.add(NewsDto.builder()
                                .title(title)
                                .url(url)
                                .dateTime(dateTime)
                                .build()
                        );
                    }
                    news = rubricService.saveAllNewsWithRubric(rubric, news);
                } catch (IOException eNews) {
                    log.atWarn().log("Parsing news for rubric: {} in resource: {} HttpStatusException: {}", NAME, rubric.getName(), eNews.getMessage());
                }
            });
        } catch (IOException e) {
            log.atWarn().log("Parsing resource {} IOException: {}", NAME, e.getMessage());
        }
        log.atInfo().log("Parsing {} ended", NAME);
    }

    /**
     * Получает список новостей рубрики
     */
    private List<Element> getNewsItems(Document rubricDoc) {
        //Текущая реализация - достает все существующие card- верхнего уровня
        return rubricDoc.select("[class*='card-']").stream()
                .filter(x -> Pattern.matches("^card-[^_]+($| .*$)", x.attr("class")))
                .toList();
    }


}
