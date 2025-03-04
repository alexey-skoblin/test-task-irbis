package com.alexey.skoblin.test_task_irbis.cron;

import com.alexey.skoblin.test_task_irbis.output.parser.LentaParser;
import com.alexey.skoblin.test_task_irbis.output.parser.RiaRuParser;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SchedulerParsers {

    private LentaParser lentaParser;

    private RiaRuParser riaRuParser;

    @Scheduled(fixedDelay = 100000)
    private void LentaRuParsesing() throws InterruptedException {
        //TODO: ПОВТОРЯЕМОСТЬ ЭЛЕМЕНТОВ ПАРСИНГА ПЕРЕПРОВЕРИТЬ
        //lentaParser.parse();
        riaRuParser.parse();
    }

}
