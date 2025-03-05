package com.alexey.skoblin.test_task_irbis.cron;

import com.alexey.skoblin.test_task_irbis.dto.ResourceDto;
import com.alexey.skoblin.test_task_irbis.output.parser.LentaParser;
import com.alexey.skoblin.test_task_irbis.service.DataSyncService;
import com.alexey.skoblin.test_task_irbis.service.ResourceService;
import com.alexey.skoblin.test_task_irbis.service.ResourceServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class LentaParseExecutor {

    private final String NAME = "Лента";

    private LentaParser lentaParser;
    private ResourceService resourceService;
    private DataSyncService dataSyncService;


    @Scheduled(fixedDelay = 100000)
    public void parse(){
        ResourceDto resourceDto = resourceService.findByName(NAME);
        ResourceDto resourceResultDto = lentaParser.parse(resourceDto);
        dataSyncService.updateWithSubEntities(resourceResultDto);
    }

}
