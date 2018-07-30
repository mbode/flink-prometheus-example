package com.github.mbode.flink_prometheus_example;

import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.DiscardingSink;

public class PrometheusExampleJob {
  private final ParameterTool parameters;

  public static void main(String[] args) throws Exception {
    new PrometheusExampleJob(ParameterTool.fromArgs(args)).run();
  }

  private PrometheusExampleJob(ParameterTool parameters) {
    this.parameters = parameters;
  }

  private void run() throws Exception {
    final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
    final DataStreamSource<Integer> input =
        env.addSource(new RandomSourceFunction(parameters.getInt("elements", Integer.MAX_VALUE)));

    input.map(new FlinkMetricsExposingMapFunction()).addSink(new DiscardingSink<>());

    env.execute(PrometheusExampleJob.class.getSimpleName());
  }
}
