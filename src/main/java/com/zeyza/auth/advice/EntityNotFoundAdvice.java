package com.zeyza.auth.advice;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.zeyza.auth.exception.EntityNotFoundException;

@ControllerAdvice
public class EntityNotFoundAdvice {

    @ExceptionHandler(EntityNotFoundException.class)
    ResponseEntity<Object> entityNotFoundHandler(EntityNotFoundException ex) {
        Map<String, String> body = Map.of("message", "Record not found");

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }
}
