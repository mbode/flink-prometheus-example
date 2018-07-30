package com.github.mbode.flink_prometheus_example;

import static org.assertj.core.api.Assertions.assertThat;

import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.test.util.AbstractTestBase;
import org.junit.Before;
import org.junit.Test;

public class RandomSourceFunctionTest extends AbstractTestBase {
  private StreamExecutionEnvironment env;

  @Before
  public void prepareExecutionEnvironmentAndTestSink() {
    env = StreamExecutionEnvironment.getExecutionEnvironment();
    env.setParallelism(1);

    CollectSink.values.clear();
  }

  @Test
  public void expectedNumberOfElementsIsEmitted() throws Exception {
    env.addSource(new RandomSourceFunction(42)).addSink(new CollectSink());
    env.execute();
    assertThat(CollectSink.values).hasSize(42);
  }

  @Test(timeout = 1_000)
  public void canCancel() throws Exception {
    final RandomSourceFunction function = new RandomSourceFunction(Integer.MAX_VALUE);
    function.cancel();

    env.addSource(function).addSink(new CollectSink());
    env.execute();

    assertThat(CollectSink.values).isEmpty();
  }
}
