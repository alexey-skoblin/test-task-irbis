package com.alexey.skoblin.test_task_irbis.dto;

import com.alexey.skoblin.test_task_irbis.entity.Resource;
import java.util.Set;
import lombok.Builder;
import lombok.Value;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link Resource}
 */
@Value
@Builder
public class ResourceDto implements Serializable {
    UUID id;
    String name;
    String url;
    Set<RubricDto> rubrics;
}