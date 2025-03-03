package com.alexey.skoblin.test_task_irbis.dto;

import com.alexey.skoblin.test_task_irbis.entity.News;
import lombok.Builder;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO for {@link News}
 */
@Value
@Builder
public class NewsDto implements Serializable {
    UUID id;
    String title;
    LocalDateTime dateTime;
    String url;
}