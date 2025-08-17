package com.example;

import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

/**
 * InMemoryTraceStore
 *
 * Responsibilities:
 * - Stores trace events in memory for a rolling 10-minute window.
 * - Supports retrieval by controllers (e.g., TraceJsonViewController).
 * Important:
 * - This is an **ephemeral in-memory store**:
 * - All traces are lost when the application restarts.
 * - No persistence beyond the last 10 minutes of runtime.
 */

@Component
public class InMemoryTraceStore {

    private final List<TraceEvent> traces = new CopyOnWriteArrayList<>();

    public void addTrace(TraceEvent event) {
        traces.add(event);
    }

    public List<TraceEvent> getTracesFromLast10Minutes() {
        Instant cutoff = Instant.now().minusSeconds(600);
        return traces.stream()
                .filter(t -> t.timestamp.isAfter(cutoff))
                .collect(Collectors.toList());
    }
}
