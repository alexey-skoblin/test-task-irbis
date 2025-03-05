package com.alexey.skoblin.test_task_irbis.service;

import com.alexey.skoblin.test_task_irbis.dto.ResourceDto;

public interface DataSyncService {

    ResourceDto updateWithSubEntities(ResourceDto resourceDto);

}
