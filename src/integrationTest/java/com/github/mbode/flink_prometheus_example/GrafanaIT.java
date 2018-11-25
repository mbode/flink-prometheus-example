package com.github.mbode.flink_prometheus_example;

import static org.awaitility.Awaitility.await;

import com.mashape.unirest.http.Unirest;
import org.junit.jupiter.api.Test;

class GrafanaIT {
  private static final String GRAFANA_URL =
      "http://"
          + System.getProperty("grafana.host")
          + ":"
          + Integer.getInteger("grafana.tcp.3000")
          + "/";

  @Test
  void flinkDashboardHasBeenImported() {
    await()
        .until(
            () -> {
              final String responseBody =
                  Unirest.get(GRAFANA_URL + "api/dashboards/uid/veLveEOiz")
                      .basicAuth("admin", "flink")
                      .asString()
                      .getBody();
              return responseBody.contains("Flink");
            });
  }
}
