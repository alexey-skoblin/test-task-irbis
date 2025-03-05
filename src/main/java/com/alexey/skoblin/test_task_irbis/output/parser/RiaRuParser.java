package com.alexey.skoblin.test_task_irbis.output.parser;

import com.alexey.skoblin.test_task_irbis.dto.NewsDto;
import com.alexey.skoblin.test_task_irbis.dto.ResourceDto;
import com.alexey.skoblin.test_task_irbis.dto.RubricDto;
import com.alexey.skoblin.test_task_irbis.service.NewsService;
import com.alexey.skoblin.test_task_irbis.service.ResourceService;
import com.alexey.skoblin.test_task_irbis.service.RubricService;
import com.alexey.skoblin.test_task_irbis.time.RiaDateTimeParser;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@AllArgsConstructor
@Slf4j
public class RiaRuParser {

    private static final String NAME = "РИА";

    private ResourceService resourceService;
    private RubricService rubricService;

    private RiaDateTimeParser dateTimeParser;
    private NewsService newsService;

    public void parse() {
        ResourceDto resource = resourceService.findByName(NAME);
        try {
            log.atInfo().log("Parsing {} started", NAME);
            Document doc = Jsoup.connect(resource.url()).get();

            Elements rubricItems = doc.select(".m-with-title");
            List<RubricDto> rubrics = new ArrayList<>();
            for (Element item : rubricItems) {
                Element link = item.selectFirst("[class*=link]");
                if (link != null) {
                    String name = link.text();
                    String url = link.attr("href");
                    Optional<UUID> uuid = rubricService
                            .findIdByUrlAndResourceId(resource.id(), name);
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
                    Document rubricDoc = Jsoup.connect(resource.url() + rubric.url()).get();
                    List<Element> newsItems = rubricDoc.select(".list-item");
                    List<NewsDto> news = new ArrayList<>();
                    for (Element item : newsItems) {
                        Element titleElement = item.selectFirst("meta[itemprop=name]");
                        if (titleElement == null) {
                            continue;
                        }
                        String title = titleElement.attr("content");
                        Element urlElement = item.selectFirst("a");
                        if (urlElement == null) {
                            continue;
                        }
                        String url = urlElement.attr("href");
                        Element timeElement = item.selectFirst("div[data-type=date]");
                        String timeString = timeElement != null ? timeElement.text() : "";
                        LocalDateTime dateTime;
                        try {
                            dateTime = dateTimeParser.parse(url, timeString);
                        } catch (DateTimeParseException eNewsDateTime) {
                            log.atWarn().log("Error parsing dateTime for news: " + eNewsDateTime);
                            continue;
                        }
                        Optional<UUID> uuid = newsService.findByUrl(url);
                        NewsDto newsDto = NewsDto.builder()
                                .id(uuid.orElse(null))
                                .title(title)
                                .url(url)
                                .dateTime(dateTime)
                                .build();
                        news.add(newsDto);
                    }
                    news = rubricService.saveAllNewsWithRubric(rubric, news);
                } catch (IOException eNews) {
                    log.atWarn().log("Parsing news for rubric: {} in resource: {} HttpStatusException: {}", NAME, rubric.name(), eNews.getMessage());
                }
            });
        } catch (IOException e) {
            log.atWarn().log("Parsing resource {} IOException: {}", NAME, e.getMessage());
        }
        log.atInfo().log("Parsing {} ended", NAME);
    }
}
