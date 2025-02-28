package com.alexey.skoblin.test_task_irbis.utils;

import jakarta.persistence.Entity;

import java.util.UUID;

public final class RequestUtils {

    public static UUID generatedId(Class<?> apiClass, UUID externalRequestId){
        if (externalRequestId != null) return externalRequestId;
        return UUID.randomUUID();
    }

}

