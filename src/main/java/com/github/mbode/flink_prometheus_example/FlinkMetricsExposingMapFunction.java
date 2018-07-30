package com.github.mbode.flink_prometheus_example;

import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.metrics.Counter;
import org.apache.flink.metrics.Histogram;
import org.apache.flink.runtime.metrics.DescriptiveStatisticsHistogram;

class FlinkMetricsExposingMapFunction extends RichMapFunction<Integer, Integer> {
  private static final long serialVersionUID = 1L;

  private transient Counter eventCounter;
  private transient Histogram lengthHistogram;

  @Override
  public void open(Configuration parameters) {
    eventCounter = getRuntimeContext().getMetricGroup().counter("events");
    lengthHistogram =
        getRuntimeContext()
            .getMetricGroup()
            .histogram("value_histogram", new DescriptiveStatisticsHistogram(10_000_000));
  }

  @Override
  public Integer map(Integer value) {
    eventCounter.inc();
    lengthHistogram.update(value);
    return value;
  }
}
