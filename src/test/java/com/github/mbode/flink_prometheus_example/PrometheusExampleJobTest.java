package com.github.mbode.flink_prometheus_example;

import org.apache.flink.test.util.AbstractTestBase;
import org.junit.Test;

public class PrometheusExampleJobTest extends AbstractTestBase {
  @Test
  public void jobRuns() throws Exception {
    PrometheusExampleJob.main(new String[] {"--elements", "7"});
  }
}
