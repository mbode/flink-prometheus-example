package com.github.mbode.flink_prometheus_example;

import org.apache.flink.test.util.AbstractTestBase;
import org.junit.Test;

public class ExampleJobTest extends AbstractTestBase {
  @Test
  public void jobRuns() throws Exception {
    ExampleJob.main(new String[] {});
  }
}
