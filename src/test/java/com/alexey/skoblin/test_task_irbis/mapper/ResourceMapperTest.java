package com.alexey.skoblin.test_task_irbis.mapper;

import com.alexey.skoblin.test_task_irbis.TestTaskIrbisApplication;
import com.alexey.skoblin.test_task_irbis.dto.ResourceDto;
import com.alexey.skoblin.test_task_irbis.entity.Resource;
import com.alexey.skoblin.test_task_irbis.entity.Rubric;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.Assert;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest
@EnableAutoConfiguration(exclude = {
        DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class,
        })
public class ResourceMapperTest {

    @Autowired
    public ResourceMapper resourceMapper;

    @Test
    void test(){
        Resource resource = Resource.builder()
                .id(UUID.randomUUID())
                .url("s")
                .name("RESOURCE")
                .build();
        ResourceDto resourceDto = resourceMapper.toDto(resource);
        Resource newResource = resourceMapper.toEntity(resourceDto);
        assertEquals(resource, newResource);
    }

    @Test
    void test2(){
        Resource resource = Resource.builder()
                .id(UUID.randomUUID())
                .url("s")
                .name("RESOURCE")
                .rubrics(new HashSet<>())
                .build();
        ResourceDto resourceDto = resourceMapper.toDto(resource);
        Resource newResource = resourceMapper.toEntity(resourceDto);
        assertEquals(resource, newResource);
    }

    @Test
    void test3(){
        Rubric rubric = Rubric.builder()
                .id(UUID.randomUUID())
                .url("")
                .name("RUBRIC")
                .resource(null)
                .build();
        Set <Rubric> rubrics = new HashSet<>();
        rubrics.add(rubric);
        Resource resource = Resource.builder()
                .id(UUID.randomUUID())
                .url("s")
                .name("RESOURCE")
                .rubrics(rubrics)
                .build();
        ResourceDto resourceDto = resourceMapper.toDto(resource);
        Resource newResource = resourceMapper.toEntity(resourceDto);
        assertEquals(resource, newResource);
    }
}