package com.example;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
/**
 * TraceJsonViewController
 *
 * Endpoint:
 * http://localhost:8080/traces/last-10-mins → returns trace data as JSON for the last 10 minutes.
 *
 * Purpose:
 *  - Reads from InMemoryTraceStore and returns a JSON list of recent TraceEvents.
 *  - Shows what spans/actions occurred in the last 10 minutes.
 Notes:
 * - Provides visibility into traces
 * - Used alongside TelemetryGenerator and TelemetryReplayController,
 *   which both feed data into the InMemoryTraceStore.
 *
 */

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

