package com.alexey.skoblin.test_task_irbis.repository;

import com.alexey.skoblin.test_task_irbis.entity.Rubric;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface RubricRepository extends JpaRepository<Rubric, UUID> {

    @Query("select r.id from Rubric r where r.url = ?1 and r.resource.id = ?2")
    Optional<UUID> findIdByUrlAndResourceId(String url, UUID id);


}