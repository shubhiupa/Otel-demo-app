package com.example;

import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/status")
public class StatusController {

    private final CustomMetricsController metricsController;
    private final InMemoryTraceStore traceStore;

    public StatusController(CustomMetricsController metricsController, InMemoryTraceStore traceStore) {
        this.metricsController = metricsController;
        this.traceStore = traceStore;
    }

    @GetMapping
    public Map<String, Object> getSmartStatus() {
        Map<String, Integer> metrics = metricsController.getMetrics();
        List<TraceEvent> recentTraces = traceStore.getTracesFromLast10Minutes();

        // Decide system status based on 500s
        String status = metrics.getOrDefault("http500Count", 0) > 0 ? "DEGRADED" : "HEALTHY";

        // Aggregate errors by action
        Map<String, Long> errorActions = recentTraces.stream()
                .filter(TraceEvent::isError)
                .collect(Collectors.groupingBy(t -> t.action, Collectors.counting()));

        List<String> issues = new ArrayList<>();
        if (metrics.getOrDefault("latencySpikes", 0) > 0 || metrics.getOrDefault("cpuSpikes", 0) > 0) {
            issues.add("Latency or CPU spikes detected in multiple simulated actions.");
        }
        if (!errorActions.isEmpty()) {
            String topError = Collections.max(errorActions.entrySet(), Map.Entry.comparingByValue()).getKey();
            issues.add("Most errors occurred during '" + topError + "' events.");
        }

        return Map.of(
                "status", status,
                "technology", Map.of(
                        "language", "Java",
                        "version", System.getProperty("java.version"),
                        "framework", "Spring Boot + Micrometer + OpenTelemetry"
                ),
                "last10MinSummary", metrics,
                "notableIssues", issues
        );
    }
}
