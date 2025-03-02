package com.alexey.skoblin.test_task_irbis.repository;

import com.alexey.skoblin.test_task_irbis.entity.Rubric;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RubricRepository extends JpaRepository<Rubric, UUID> {

    Rubric findByNameAndResource_Id(String name, UUID id);
}