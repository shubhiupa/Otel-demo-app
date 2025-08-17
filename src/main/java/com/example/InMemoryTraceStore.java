package com.example;

import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

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
