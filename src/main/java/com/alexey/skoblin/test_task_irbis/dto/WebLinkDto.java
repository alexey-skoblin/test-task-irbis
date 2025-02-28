package com.alexey.skoblin.test_task_irbis.dto;

import lombok.Value;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link com.alexey.skoblin.test_task_irbis.entity.WebLink}
 */
@Value
public class WebLinkDto implements Serializable {
    UUID id;
    String url;
}