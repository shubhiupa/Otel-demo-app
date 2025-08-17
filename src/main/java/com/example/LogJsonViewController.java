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

