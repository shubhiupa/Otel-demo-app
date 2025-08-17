package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


/**
 * OtelDemoApplication
 *
 * Main Spring Boot entrypoint.
 * - Boots the application context and starts the embedded server.
 * - Initializes telemetry generator and controllers.
 * - Run via IntelliJ or Maven
 */
@SpringBootApplication
@EnableScheduling
public class OtelDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(OtelDemoApplication.class, args);
    }
}
