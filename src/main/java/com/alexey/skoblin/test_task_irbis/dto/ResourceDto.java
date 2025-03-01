package com.alexey.skoblin.test_task_irbis.dto;

import com.alexey.skoblin.test_task_irbis.entity.Resource;
import lombok.Value;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link Resource}
 */
@Value
public class ResourceDto implements Serializable {
    UUID id;
    String name;
    String url;
}