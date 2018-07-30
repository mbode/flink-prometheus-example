package com.github.mbode.flink_prometheus_example;

import java.util.concurrent.ThreadLocalRandom;
import org.apache.flink.streaming.api.functions.source.SourceFunction;

class RandomSourceFunction implements SourceFunction<Integer> {
  private int count = 0;
  private volatile boolean isRunning = true;
  private int elements;

  RandomSourceFunction(int elements) {
    this.elements = elements;
  }

  public void run(SourceContext<Integer> ctx) throws InterruptedException {
    while (isRunning && count < elements) {
      Thread.sleep(1);
      ctx.collect(ThreadLocalRandom.current().nextInt(10_000));
      count++;
    }
  }

  public void cancel() {
    isRunning = false;
  }
}
