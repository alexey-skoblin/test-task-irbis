package com.alexey.skoblin.test_task_irbis.handler;

import com.alexey.skoblin.test_task_irbis.exception.EntityNotFoundByIdException;
import com.alexey.skoblin.test_task_irbis.exception.EntityNotFoundByNameException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundByIdException.class)
    public ResponseEntity<String> handleException(EntityNotFoundByIdException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(EntityNotFoundByNameException.class)
    public ResponseEntity<String> handleException(EntityNotFoundByNameException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }


}
