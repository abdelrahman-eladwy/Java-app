package com.security.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Main Spring Boot Application
 * Demonstrates secure application development with comprehensive security scanning
 */
@SpringBootApplication
@EnableCaching
@EnableJpaAuditing
public class SecureSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecureSpringApplication.class, args);
    }
}
