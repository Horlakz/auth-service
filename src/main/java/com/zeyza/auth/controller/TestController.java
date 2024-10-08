package com.zeyza.auth.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/test/")
    public ResponseEntity<?> doTest() {
        Map<String, String> map = new HashMap<>();
        map.put("message", "Hello World!");
        return ResponseEntity.ok(map);

    }

    @GetMapping("/health/")
    public ResponseEntity<?> doHealth() {
        Map<String, String> map = new HashMap<>();
        map.put("message", "OK");
        return ResponseEntity.ok(map);

    }
}
