package com.example;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

@RestController
public class CustomMetricsController {

    private final ConcurrentLinkedQueue<Instant> errorEvents = new ConcurrentLinkedQueue<>();
    private final ConcurrentLinkedQueue<Instant> latencySpikes = new ConcurrentLinkedQueue<>();
    private final ConcurrentLinkedQueue<Instant> cpuSpikes = new ConcurrentLinkedQueue<>();

    private final ConcurrentLinkedQueue<Instant> totalEvents = new ConcurrentLinkedQueue<>();
    private final ConcurrentLinkedQueue<Instant> http200s = new ConcurrentLinkedQueue<>();
    private final ConcurrentLinkedQueue<Instant> http500s = new ConcurrentLinkedQueue<>();

    public void recordErrorEvent() {
        errorEvents.add(Instant.now());
    }

    public void recordLatencySpike() {
        latencySpikes.add(Instant.now());
    }

    public void recordCpuSpike() {
        cpuSpikes.add(Instant.now());
    }

    public void recordTotalEvent() {
        totalEvents.add(Instant.now());
    }

    public void recordHttp200() {
        http200s.add(Instant.now());
    }

    public void recordHttp500() {
        http500s.add(Instant.now());
    }

    @GetMapping("/metrics/last-10-mins")
    public Map<String, Integer> getMetrics() {
        Instant cutoff = Instant.now().minusSeconds(600);

        return Map.of(
                "errorEvents", countSince(errorEvents, cutoff),
                "latencySpikes", countSince(latencySpikes, cutoff),
                "cpuSpikes", countSince(cpuSpikes, cutoff),
                "totalEvents", countSince(totalEvents, cutoff),
                "http200Count", countSince(http200s, cutoff),
                "http500Count", countSince(http500s, cutoff)
        );
    }

    private int countSince(Queue<Instant> events, Instant cutoff) {
        List<Instant> valid = events.stream()
                .filter(t -> t.isAfter(cutoff))
                .collect(Collectors.toList());

        // cleanup old events
        events.retainAll(valid);

        return valid.size();
    }
}
