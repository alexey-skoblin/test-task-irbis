package com.alexey.skoblin.test_task_irbis.cron;

import com.alexey.skoblin.test_task_irbis.dto.ResourceDto;
import com.alexey.skoblin.test_task_irbis.output.parser.RiaRuParser;
import com.alexey.skoblin.test_task_irbis.service.DataSyncService;
import com.alexey.skoblin.test_task_irbis.service.ResourceService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class RiaRuParseExecutor {

    private final String NAME = "РИА";

    private RiaRuParser riaRuParser;
    private ResourceService resourceService;
    private DataSyncService dataSyncService;


    @Scheduled(fixedDelay = 10000_000)
    public void parse(){
        ResourceDto resourceDto = resourceService.findByName(NAME);
        ResourceDto resourceResultDto = riaRuParser.parse(resourceDto);
        dataSyncService.updateWithSubEntities(resourceResultDto);
        log.atInfo().log("Parsing entities from {} saved", NAME);
    }

}
