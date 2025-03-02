package com.alexey.skoblin.test_task_irbis.cron;

import com.alexey.skoblin.test_task_irbis.output.parser.LentaParser;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SchedulerParsers {

//    @Scheduled
//    public void LentaRuParsesing(){
//
//    }

    private LentaParser lentaParser;

    @Scheduled(fixedDelay = 10000)
    private void LentaRuParsesing(){
        lentaParser.parse();
    }

}
