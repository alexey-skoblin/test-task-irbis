package com.alexey.skoblin.test_task_irbis.dto;

import com.alexey.skoblin.test_task_irbis.entity.Resource;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.util.HashSet;
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
    @Builder.Default
    Set<RubricDto> rubrics = new HashSet<>();
}