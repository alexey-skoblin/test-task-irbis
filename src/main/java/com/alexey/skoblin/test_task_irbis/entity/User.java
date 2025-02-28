package com.alexey.skoblin.test_task_irbis.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

//В бд
@Entity
public class User {

    @Id
    @Column(name = "id")
    private UUID id;

    @PrePersist
    public void generateId() {
        if (id != null) {
            id = UUID.randomUUID();
        }
    }

    private String name;
}
