package com.security.app.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Health Check Controller
 * Provides public endpoints for health checks and monitoring
 */
@RestController
@RequestMapping("/api/public")
@Tag(name = "Public APIs", description = "Publicly accessible endpoints")
public class HealthController {

    @GetMapping("/health")
    @Operation(summary = "Health check", description = "Check if the application is running")
    public ResponseEntity<Map<String, Object>> health() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "UP");
        response.put("timestamp", LocalDateTime.now());
        response.put("application", "Secure Spring Application");
        response.put("version", "1.0.0");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/info")
    @Operation(summary = "Application info", description = "Get application information")
    public ResponseEntity<Map<String, Object>> info() {
        Map<String, Object> response = new HashMap<>();
        response.put("name", "Secure Spring Application");
        response.put("description", "Enterprise Spring Boot application with comprehensive security scanning");
        response.put("version", "1.0.0");
        response.put("framework", "Spring Boot 3.2.0");
        response.put("java", System.getProperty("java.version"));
        return ResponseEntity.ok(response);
    }
}
