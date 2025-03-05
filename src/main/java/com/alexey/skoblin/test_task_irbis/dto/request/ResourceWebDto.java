package com.alexey.skoblin.test_task_irbis.dto.request;

import java.util.List;
import java.util.UUID;

/**
 * DTO for {@link com.alexey.skoblin.test_task_irbis.entity.Resource}
 */
public record ResourceWebDto(UUID id, String name, String url, List<UUID> rubricIds) {
}