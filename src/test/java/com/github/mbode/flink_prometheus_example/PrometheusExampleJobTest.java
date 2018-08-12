package com.github.mbode.flink_prometheus_example;

import org.apache.flink.test.util.AbstractTestBase;
import org.junit.jupiter.api.Test;

class PrometheusExampleJobTest extends AbstractTestBase {
  @Test
  void jobRuns() throws Exception {
    PrometheusExampleJob.main(new String[] {"--elements", "7"});
  }
}
