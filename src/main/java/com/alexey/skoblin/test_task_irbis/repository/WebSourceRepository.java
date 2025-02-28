package com.alexey.skoblin.test_task_irbis.repository;

import com.alexey.skoblin.test_task_irbis.entity.WebSource;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface WebSourceRepository extends JpaRepository<WebSource, UUID> {
}