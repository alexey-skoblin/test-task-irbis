package com.alexey.skoblin.test_task_irbis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TestTaskIrbisApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestTaskIrbisApplication.class, args);
    }

}
