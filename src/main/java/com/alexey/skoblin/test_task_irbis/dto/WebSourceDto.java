package com.alexey.skoblin.test_task_irbis.dto;

import com.alexey.skoblin.test_task_irbis.entity.WebSource;
import lombok.Value;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link WebSource}
 */
@Value
public class WebSourceDto implements Serializable {
    UUID id;
    String name;
    WebLinkDto link;
}