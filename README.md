# Otel Demo App

This is a **Spring Boot application** that generates synthetic **OpenTelemetry logs, metrics, and traces** for demo and testing purposes.

---

## 📂 Project Structure

- **`TelemetryGenerator.java`** → Generates random telemetry data in memory.
- **`TraceJsonViewController.java`** → Exposes trace data as JSON.
- **`LogJsonViewController.java`** → Exposes logs as JSON.
- **`CustomMetricsController.java`** → Exposes metrics.
- **`TelemetryReplayController.java`** → Exposes a “replay” view of telemetry for the last 10 minutes.
- **`StatusController.java`** → Health/status endpoint(s).
- **`OtelDemoApplication.java`** → Main Spring Boot entry point.

---

## 🚀 Running Locally

### Prerequisites
- Java 17+
- Maven (`mvn`)
- IntelliJ IDEA (or command line)

By default, the app runs on http://localhost:8080.

End Points

GET	/traces/last-10-mins	Returns traces from the last 10 minutes

GET	/logs/last-10-mins/json	Returns logs from the last 10 minutes in JSON

GET	/metrics/last-10-mins	Returns metrics from the last 10 minutes

GET	/replay-last-10-mins	Returns a replay of telemetry data

GET	/status (if defined)	Health/status info
