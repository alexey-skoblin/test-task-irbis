package com.alexey.skoblin.test_task_irbis.dto;

import com.alexey.skoblin.test_task_irbis.entity.Rubric;
import lombok.Value;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/**
 * DTO for {@link Rubric}
 */
@Value
public class RubricDto implements Serializable {
    UUID id;
    String name;
    List<NewsDto> news;
}