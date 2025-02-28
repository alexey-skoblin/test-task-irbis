package com.alexey.skoblin.test_task_irbis.dto;

import com.alexey.skoblin.test_task_irbis.entity.WebNews;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO for {@link WebNews}
 */
@Value
public class WebNewsDto implements Serializable {
    UUID id;
    String title;
    LocalDateTime dateTime;
    WebLinkDto link;
}