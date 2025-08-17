package com.example;

import java.time.Instant;
import java.util.UUID;

public class TraceEvent {
    public String traceId;
    public String spanId;
    public String action;
    public int userId;
    public Instant timestamp;
    private boolean isError;
    public String errorType;

    public TraceEvent(String traceId, String spanId, String action, int userId, Instant timestamp, boolean isError, String errorType) {
        this.traceId = traceId;
        this.spanId = spanId;
        this.action = action;
        this.userId = userId;
        this.timestamp = timestamp;
        this.isError = isError;
        this.errorType = errorType;
    }

    public boolean isError() {
        return isError;
    }
}
