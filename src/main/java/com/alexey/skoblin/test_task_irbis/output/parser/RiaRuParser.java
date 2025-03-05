package com.alexey.skoblin.test_task_irbis.output.parser;

import com.alexey.skoblin.test_task_irbis.dto.ResourceDto;
import com.alexey.skoblin.test_task_irbis.entity.News;
import com.alexey.skoblin.test_task_irbis.entity.Resource;
import com.alexey.skoblin.test_task_irbis.entity.Rubric;
import com.alexey.skoblin.test_task_irbis.mapper.ResourceMapper;
import com.alexey.skoblin.test_task_irbis.time.RiaDateTimeParser;
import com.alexey.skoblin.test_task_irbis.utils.JsoupUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.random.RandomGenerator;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
@Slf4j
public class RiaRuParser {

    private static final String RUBRIC_ITEM_SELECTOR = ".m-with-title";
    private static final String RUBRIC_LINK_SELECTOR = "[class*=link]";
    private static final String NEWS_ITEM_SELECTOR = ".list-item";
    private static final String NEWS_TITLE_SELECTOR = "meta[itemprop=name]";
    private static final String NEWS_URL_SELECTOR = "a";
    private static final String NEWS_DATE_SELECTOR = "div[data-type=date]";

    private final RiaDateTimeParser dateTimeParser;
    private final ResourceMapper resourceMapper;

    private final int SLEEP_SECONDS = 60;

    public ResourceDto parse(ResourceDto resourceDto) {
        try {
            log.atInfo().log("Parsing {} started", resourceDto.name());
            Document mainPageDoc = JsoupUtils.createHumanizedConnection(resourceDto.url()).get();
            Resource resultResource = resourceMapper.toEntity(resourceDto);

            resultResource.setRubrics(parseRubrics(resultResource, mainPageDoc));
            resultResource.getRubrics().forEach(rubric -> {
                try {
                    TimeUnit.SECONDS.sleep(SLEEP_SECONDS + RandomGenerator.getDefault().nextInt(SLEEP_SECONDS));
                } catch (InterruptedException ie) {
                    log.warn("InterruptedException: {}", ie.getMessage());
                }
                rubric.getNews().addAll(parseNews(rubric, resourceDto.url()));
            });

            return resourceMapper.toDto(resultResource);
        } catch (IOException e) {
            log.atWarn().log("Parsing resourceDto {} IOException: {}", resourceDto.name(), e.getMessage());
        }

        log.atInfo().log("Parsing {} ended", resourceDto.name());
        return resourceDto;
    }

    private List<Rubric> parseRubrics(Resource resource, Document document) {
        return document.select(RUBRIC_ITEM_SELECTOR).stream()
                .map(this::parseRubricCard)
                .filter(Objects::nonNull)
//                .peek(rubric -> rubric.setResource(resource))
                .collect(Collectors.toList());
    }

    private Rubric parseRubricCard(Element item) {
        try {
            Element link = item.selectFirst(RUBRIC_LINK_SELECTOR);
            if (link == null) {
                return null;
            }
            String url = link.attr("href");
            if (url.isEmpty()) {
                return null;
            }
            return Rubric.builder()
                    .name(link.text())
                    .url(url)
                    .news(new ArrayList<>())
                    .build();
        } catch (Exception e) {
            log.atWarn().log("Error parsing news item: {} in item {}", e.getMessage(), item);
            return null;
        }
    }

    private List<News> parseNews(Rubric rubric, String baseUrl) {
        try {
            Document rubricDoc = JsoupUtils.createHumanizedConnection(baseUrl + rubric.getUrl()).get();
            return rubricDoc.select(NEWS_ITEM_SELECTOR).stream()
                    .map(this::parseNewsCard)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            log.atWarn().log("Error parsing rubric: {}, error: {}", rubric.getName(), e.getMessage());
        }
        return Collections.emptyList();
    }

    private News parseNewsCard(Element card) {
        try {
            Element titleElement = card.selectFirst(NEWS_TITLE_SELECTOR);
            Element urlElement = card.selectFirst(NEWS_URL_SELECTOR);
            Element timeElement = card.selectFirst(NEWS_DATE_SELECTOR);

            if (titleElement == null || urlElement == null) {
                return null;
            }

            String title = titleElement.attr("content");
            String url = urlElement.attr("href");
            String timeString = timeElement != null ? timeElement.text() : "";

            LocalDateTime dateTime = parseNewsDate(url, timeString);

            return News.builder()
                    .title(title)
                    .url(url)
                    .dateTime(dateTime)
                    .build();
        } catch (Exception e) {
            log.atWarn().log("Error parsing news card : {} in item {}", e.getMessage(), card);
            return null;
        }
    }

    private LocalDateTime parseNewsDate(String url, String timeString) {
        try {
            return dateTimeParser.parse(url, timeString);
        } catch (DateTimeParseException e) {
            log.atWarn().log("Error parsing date for news: {} cause {}", url, e.getMessage());
            return null;
        }
    }
}