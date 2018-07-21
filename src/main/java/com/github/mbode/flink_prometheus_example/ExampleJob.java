package com.github.mbode.flink_prometheus_example;

import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class ExampleJob {
  public static void main(String[] args) throws Exception {
    final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
    env.fromElements("one", "two", "three").print();
    env.execute("Example");
  }
}
