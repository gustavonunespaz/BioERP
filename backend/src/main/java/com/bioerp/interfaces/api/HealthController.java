package com.bioerp.interfaces.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.OffsetDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/health")
public class HealthController {

    @GetMapping
    public ResponseEntity<Map<String, Object>> health() {
        Map<String, Object> payload = Map.of(
                "status", "UP",
                "timestamp", OffsetDateTime.now().toString()
        );
        return ResponseEntity.ok(payload);
    }
}
