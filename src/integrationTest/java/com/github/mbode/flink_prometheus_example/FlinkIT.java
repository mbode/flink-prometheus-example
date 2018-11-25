package com.github.mbode.flink_prometheus_example;

import static org.awaitility.Awaitility.await;

import com.mashape.unirest.http.Unirest;
import java.util.concurrent.TimeUnit;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

class FlinkIT {
  private static final String FLINK_URL =
      "http://"
          + System.getProperty("job-cluster.host")
          + ":"
          + Integer.getInteger("job-cluster.tcp.8081")
          + "/";

  @Test
  void exampleJobIsRunning() {
    await()
        .atMost(1, TimeUnit.MINUTES)
        .until(
            () -> {
              final JSONObject responseJson =
                  Unirest.get(FLINK_URL + "jobs/00000000000000000000000000000000")
                      .asJson()
                      .getBody()
                      .getObject();
              return "PrometheusExampleJob".equals(responseJson.getString("name"))
                  && "RUNNING".equals(responseJson.getString("state"));
            });
  }
}
