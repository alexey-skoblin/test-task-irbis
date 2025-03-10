package com.alexey.skoblin.test_task_irbis.dto;

import com.alexey.skoblin.test_task_irbis.entity.Rubric;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * DTO for {@link Rubric}
 */
@Builder
public record RubricDto(
        UUID id,
        String name,
        String url,
        @NotNull
        List<NewsDto> news,
        ResourceDto resource) {
}