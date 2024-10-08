package com.zeyza.auth.advice;

import org.apache.catalina.connector.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.zeyza.auth.exception.RoleException;

@ControllerAdvice
public class RoleAdvice {

    @ExceptionHandler(RoleException.class)
    public ResponseEntity<String> handleRoleException(RoleException ex) {
        return ResponseEntity.status(Response.SC_BAD_REQUEST).body(ex.getMessage());
    }
}
