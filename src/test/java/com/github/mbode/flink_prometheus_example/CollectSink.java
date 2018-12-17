package com.github.mbode.flink_prometheus_example;

import java.util.ArrayList;
import java.util.List;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;

class CollectSink implements SinkFunction<Integer> {
  static final List<Integer> values = new ArrayList<>();

  @Override
  public void invoke(Integer value, Context context) {
    values.add(value);
  }
}
