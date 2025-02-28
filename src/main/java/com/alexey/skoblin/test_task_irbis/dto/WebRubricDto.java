package com.alexey.skoblin.test_task_irbis.dto;

import com.alexey.skoblin.test_task_irbis.entity.WebRubric;
import lombok.Value;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/**
 * DTO for {@link WebRubric}
 */
@Value
public class WebRubricDto implements Serializable {
    UUID id;
    String name;
    WebLinkDto link;
    List<WebNewsDto> news;
}