FROM gradle:7.4.2-jdk11 as builder
COPY . .
RUN gradle shadowJar

FROM flink:1.15.0-java11
RUN echo "metrics.reporters: prom" >> "$FLINK_HOME/conf/flink-conf.yaml"; \
    echo "metrics.reporter.prom.class: org.apache.flink.metrics.prometheus.PrometheusReporter" >> "$FLINK_HOME/conf/flink-conf.yaml"
COPY --from=builder /home/gradle/build/libs/*.jar $FLINK_HOME/usrlib/
RUN mkdir /state && chown flink:flink /state  # workaround for https://github.com/docker/compose/issues/3270
