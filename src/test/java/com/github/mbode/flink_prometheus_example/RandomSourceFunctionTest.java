package com.github.mbode.flink_prometheus_example;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.test.util.AbstractTestBase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RandomSourceFunctionTest extends AbstractTestBase {
  private StreamExecutionEnvironment env;

  @BeforeEach
  void prepareExecutionEnvironmentAndTestSink() {
    env = StreamExecutionEnvironment.getExecutionEnvironment();
    env.setParallelism(1);

    CollectSink.values.clear();
  }

  @Test
  void expectedNumberOfElementsIsEmitted() throws Exception {
    env.addSource(new RandomSourceFunction(42)).addSink(new CollectSink());
    env.execute();
    assertThat(CollectSink.values).hasSize(42);
  }

  @Test
  void canCancel() {
    final RandomSourceFunction function = new RandomSourceFunction(Integer.MAX_VALUE);
    function.cancel();

    env.addSource(function).addSink(new CollectSink());
    Assertions.assertTimeout(Duration.ofSeconds(10), () -> env.execute());

    assertThat(CollectSink.values).isEmpty();
  }
}
