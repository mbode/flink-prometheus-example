package com.github.mbode.flink_prometheus_example;

import static org.awaitility.Awaitility.await;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import java.util.concurrent.TimeUnit;
import org.json.JSONArray;
import org.junit.jupiter.api.Test;

class IntegrationTest {
  @Test
  void flinkDashboardHasBeenImportedIntoGrafana() {
    await()
        .until(
            () -> {
              final String responseBody =
                  Unirest.get("http://localhost:3000/api/dashboards/uid/veLveEOiz")
                      .basicAuth("admin", "flink")
                      .asString()
                      .getBody();
              return responseBody.contains("Flink");
            });
  }

  @Test
  void genericFlinkMetricsArriveInPrometheus() {
    await().until(() -> dataIsAvailableInPrometheusFor("flink_jobmanager_job_uptime"));
  }

  @Test
  void exampleJobMetricsArriveInPrometheus() {
    await()
        .atMost(1, TimeUnit.MINUTES)
        .until(
            () ->
                dataIsAvailableInPrometheusFor("flink_taskmanager_job_task_operator_events")
                    && dataIsAvailableInPrometheusFor(
                        "flink_taskmanager_job_task_operator_value_histogram"));
  }

  private static boolean dataIsAvailableInPrometheusFor(String metricsName)
      throws UnirestException {
    final JSONArray resultArray =
        Unirest.get("http://localhost:9090/api/v1/query?query=" + metricsName)
            .asJson()
            .getBody()
            .getObject()
            .getJSONObject("data")
            .getJSONArray("result");
    return resultArray.length() > 0;
  }
}
