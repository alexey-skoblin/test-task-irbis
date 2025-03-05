package com.alexey.skoblin.test_task_irbis.output.parser;

import com.alexey.skoblin.test_task_irbis.dto.ResourceDto;
import com.alexey.skoblin.test_task_irbis.entity.News;
import com.alexey.skoblin.test_task_irbis.entity.Resource;
import com.alexey.skoblin.test_task_irbis.entity.Rubric;
import com.alexey.skoblin.test_task_irbis.mapper.ResourceMapper;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.alexey.skoblin.test_task_irbis.time.DateTimeParser;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class LentaParser {
    private static final String RUBRIC_ITEM_SELECTOR = ".menu__nav-list > li.menu__nav-item:has(a.menu__nav-link._is-extra)";
    private static final String RUBRIC_LINK_SELECTOR = "a.menu__nav-link";
    private static final String NEWS_CARD_SELECTOR = "[class*='card-']";
    private static final Pattern NEWS_CARD_PATTERN = Pattern.compile("^card-[^_]+($| .*$)");

    private final DateTimeParser dateTimeParser;
    private ResourceMapper resourceMapper;

    private final int SLEEP_SECONDS = 1;

    public ResourceDto parse(ResourceDto resourceDto) {
        try {
            log.atInfo().log("Parsing {} started", resourceDto.name());
            Document mainPageDoc = Jsoup.connect(resourceDto.url()).get();
            Resource resultResource = resourceMapper.toEntity(resourceDto);
            resultResource.setRubrics(parseRubrics(resultResource, mainPageDoc));
            resultResource.getRubrics().forEach(rubric -> {
                try {
                    TimeUnit.SECONDS.sleep(SLEEP_SECONDS);
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
                .map(LentaParser::parseRubricCard)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private static Rubric parseRubricCard(Element item) {
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
                    .build();
        } catch (Exception e) {
            log.atWarn().log("Error parsing news item: {} in item {}", e.getMessage(), item);
            return null;
        }
    }

    private List<News> parseNews(Rubric rubric, String baseUrl) {
        try {
            Document rubricDoc = Jsoup.connect(baseUrl + rubric.getUrl()).get();
            return rubricDoc.select(NEWS_CARD_SELECTOR).stream()
                    .filter(this::isValidNewsCard)
                    .map(this::parseNewsCard)
                    .filter(Objects::nonNull)
//                    .peek(news -> news.setRubric(rubric))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            log.atWarn().log("Error parsing rubric: {}, error: {}", rubric.getName(), e.getMessage());
        }
        return new ArrayList<>();
    }

    private boolean isValidNewsCard(Element card) {
        //Проверка на карточку верхнего уровня
        return NEWS_CARD_PATTERN.matcher(card.attr("class")).matches();
    }

    private News parseNewsCard(Element card) {
        try {
            Element titleElement = card.selectFirst("[class*='title']");
            if (titleElement == null) {
                return null;
            }
            Element timeElement = card.selectFirst("time");
            if (timeElement == null) {
                return null;
            }
            String title = titleElement.text();
            String relativeUrl = card.attr("href");

            LocalDateTime date = parseNewsDate(timeElement, relativeUrl);
            return News.builder()
                    .title(title)
                    .url(relativeUrl)
                    .dateTime(date)
                    .build();
        } catch (Exception e) {
            log.atWarn().log("Error parsing news card : {} in item {}", e.getMessage(), card);
            return null;
        }
    }

    private LocalDateTime parseNewsDate(Element timeElement, String url) {
        try {
            return dateTimeParser.parse(url, timeElement.text());
        } catch (DateTimeParseException e) {
            log.atWarn().log("Error parsing date for news: {} cause {}", url, e.getMessage());
            return null;
        }
    }
}
