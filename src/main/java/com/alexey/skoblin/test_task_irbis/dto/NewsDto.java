package com.alexey.skoblin.test_task_irbis.dto;

import com.alexey.skoblin.test_task_irbis.entity.News;
import com.alexey.skoblin.test_task_irbis.entity.Rubric;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Builder;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;
import org.mapstruct.Mapping;

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
    RubricDto rubric;
}