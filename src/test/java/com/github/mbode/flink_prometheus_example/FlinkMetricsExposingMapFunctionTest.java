package com.github.mbode.flink_prometheus_example;

import static org.assertj.core.api.Assertions.assertThat;

import org.apache.flink.metrics.Counter;
import org.apache.flink.metrics.Histogram;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class FlinkMetricsExposingMapFunctionTest {
  private static final Integer TEST_VALUE = 42;

  @Mock private Counter eventCounter;

  @Mock private Histogram valueHistogram;

  @InjectMocks
  private final FlinkMetricsExposingMapFunction flinkMetricsExposingMapFunction =
      new FlinkMetricsExposingMapFunction();

  @Test
  public void mapActsAsIdentity() {
    assertThat(flinkMetricsExposingMapFunction.map(TEST_VALUE)).isEqualTo(TEST_VALUE);
  }

  @Test
  public void eventsAreCounted() {
    flinkMetricsExposingMapFunction.map(TEST_VALUE);
    Mockito.verify(eventCounter).inc();
  }

  @Test
  public void valueIsReportedToHistogram() {
    flinkMetricsExposingMapFunction.map(TEST_VALUE);
    Mockito.verify(valueHistogram).update(TEST_VALUE);
  }
}
