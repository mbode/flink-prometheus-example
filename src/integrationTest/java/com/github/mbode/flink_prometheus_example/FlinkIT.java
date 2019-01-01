package com.github.mbode.flink_prometheus_example;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

import com.github.dockerjava.core.DockerClientBuilder;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

class FlinkIT {
  private static final String FLINK_URL =
      "http://"
          + System.getProperty("job-cluster.host")
          + ":"
          + Integer.getInteger("job-cluster.tcp.8081")
          + "/";
  private static final String JOB_ID = "00000000000000000000000000000000";

  @Test
  void exampleJobIsRunning() {
    await().atMost(1, TimeUnit.MINUTES).ignoreException(JSONException.class).until(jobIsRunning());
  }

  @Test
  void jobCanBeRestartedFromCheckpoint() throws UnirestException {
    await()
        .atMost(1, TimeUnit.MINUTES)
        .ignoreException(JSONException.class)
        .untilAsserted(() -> assertThat(getActiveTaskManager()).doesNotContain("unassigned"));

    final String firstActiveTaskManager = getActiveTaskManager();

    DockerClientBuilder.getInstance().build().killContainerCmd(firstActiveTaskManager).exec();

    await()
        .atMost(1, TimeUnit.MINUTES)
        .untilAsserted(
            () -> assertThat(getActiveTaskManager()).isNotEqualTo(firstActiveTaskManager));
  }

  private static String getActiveTaskManager() throws UnirestException {
    final String vertexId =
        Unirest.get(FLINK_URL + "jobs/" + JOB_ID)
            .asJson()
            .getBody()
            .getObject()
            .getJSONArray("vertices")
            .getJSONObject(0)
            .getString("id");
    return Unirest.get(FLINK_URL + "jobs/" + JOB_ID + "/vertices/" + vertexId)
        .asJson()
        .getBody()
        .getObject()
        .getJSONArray("subtasks")
        .getJSONObject(0)
        .getString("host")
        .split(":")[0];
  }

  private Callable<Boolean> jobIsRunning() {
    return () -> {
      final JSONObject responseJson =
          Unirest.get(FLINK_URL + "jobs/" + JOB_ID).asJson().getBody().getObject();
      return "PrometheusExampleJob".equals(responseJson.getString("name"))
          && "RUNNING".equals(responseJson.getString("state"));
    };
  }
}
