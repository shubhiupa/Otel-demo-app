package com.example;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TraceJsonViewController {

    private final InMemoryTraceStore traceStore;

    public TraceJsonViewController(InMemoryTraceStore traceStore) {
        this.traceStore = traceStore;
    }

    @GetMapping("/traces/last-10-mins")
    public List<TraceEvent> getTracesFromLast10Minutes() {
        return traceStore.getTracesFromLast10Minutes();
    }
}

