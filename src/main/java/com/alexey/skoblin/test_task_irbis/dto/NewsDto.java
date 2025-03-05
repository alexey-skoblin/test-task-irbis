package com.alexey.skoblin.test_task_irbis.dto;

import com.alexey.skoblin.test_task_irbis.entity.News;
import com.alexey.skoblin.test_task_irbis.entity.Rubric;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.mapstruct.Mapping;

/**
 * DTO for {@link News}
 */
@Builder
public record NewsDto(
        UUID id,
        String title,
        LocalDateTime dateTime,
        String url,
        List<RubricDto> rubrics) {
}