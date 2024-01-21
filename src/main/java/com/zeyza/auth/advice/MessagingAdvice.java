package com.zeyza.auth.advice;

import java.util.Map;

import org.apache.catalina.connector.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.mail.MessagingException;

@ControllerAdvice
public class MessagingAdvice {
    @ExceptionHandler(MessagingException.class)
    public ResponseEntity<?> handleMessagingException(MessagingException ex) {
        Map<String, String> body = Map.of("message", ex.getMessage());

        return ResponseEntity.status(Response.SC_BAD_REQUEST).body(body);
    }
}
