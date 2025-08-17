package com.example;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;
import java.nio.file.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * LogJsonViewController
 *
 * Endpoint:
 * - GET http://localhost:8080/logs/last-10-mins/json  → returns structured logs from the last 10 minutes.
 * Purpose:
 * - Reads structured JSON logs from the file "logs/app.json".
 * - Filters to only include logs from the last 10 minutes.
 * - Returns them as a JSON array of log objects.
 *
 * Important:
 *  - By default, Spring Boot logs go to console in plain text.
 *  - For this endpoint to return data, configure logging to output JSON to logs/app.json
 */


@RestController
public class LogJsonViewController {

    private static final String LOG_FILE_PATH = "logs/app.json";
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping("/logs/last-10-mins/json")
    public List<JsonNode> getLastTenMinutesLogs() throws IOException {
        List<JsonNode> recentLogs = new ArrayList<>();
        List<String> lines = Files.readAllLines(Paths.get(LOG_FILE_PATH));
        Instant cutoff = Instant.now().minusSeconds(600);

        for (String line : lines) {
            try {
                JsonNode log = objectMapper.readTree(line);
                if (log.has("@timestamp")) {
                    Instant ts = Instant.parse(log.get("@timestamp").asText());
                    if (ts.isAfter(cutoff)) {
                        recentLogs.add(log);
                    }
                }
            } catch (Exception ignored) {
                // skip malformed lines
            }
        }

        return recentLogs;
    }
}

