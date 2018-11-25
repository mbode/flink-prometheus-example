package com.github.mbode.flink_prometheus_example;

import org.apache.flink.api.java.utils.ParameterTool;
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
    env.enableCheckpointing(500);
    env.disableOperatorChaining();

    env.addSource(new RandomSourceFunction(parameters.getInt("elements", Integer.MAX_VALUE)))
        .name(RandomSourceFunction.class.getSimpleName())
        .map(new FlinkMetricsExposingMapFunction())
        .name(FlinkMetricsExposingMapFunction.class.getSimpleName())
        .addSink(new DiscardingSink<>())
        .name(DiscardingSink.class.getSimpleName());

    env.execute(PrometheusExampleJob.class.getSimpleName());
  }
}
