package com.github.mbode.flink_prometheus_example;

import static org.awaitility.Awaitility.await;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DockerClientBuilder;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import java.util.concurrent.Callable;
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
  private static final String JOB_ID = "00000000000000000000000000000000";

  @Test
  void exampleJobIsRunning() {
    await().atMost(1, TimeUnit.MINUTES).until(jobIsRunning());
  }

  @Test
  void jobCanBeRestartedFromCheckpoint() throws UnirestException {
    await().atMost(1, TimeUnit.MINUTES).until(jobIsRunning());
    final String vertexId =
        Unirest.get(FLINK_URL + "jobs/" + JOB_ID)
            .asJson()
            .getBody()
            .getObject()
            .getJSONArray("vertices")
            .getJSONObject(0)
            .getString("id");
    final String activeTaskManager =
        Unirest.get(FLINK_URL + "jobs/" + JOB_ID + "/vertices/" + vertexId)
            .asJson()
            .getBody()
            .getObject()
            .getJSONArray("subtasks")
            .getJSONObject(0)
            .getString("host");
    final DockerClient client = DockerClientBuilder.getInstance().build();
    client.killContainerCmd(activeTaskManager);
    await().atMost(1, TimeUnit.MINUTES).until(jobIsRunning());
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
