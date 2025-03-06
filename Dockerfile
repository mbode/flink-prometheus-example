FROM gradle:8.13-jdk11 as builder
COPY . .
RUN gradle shadowJar

FROM flink:1.20.1-java11
RUN echo "metrics.reporters: prom" >> "$FLINK_HOME/conf/config.yaml"; \
    echo "metrics.reporter.prom.factory.class: org.apache.flink.metrics.prometheus.PrometheusReporterFactory" >> "$FLINK_HOME/conf/config.yaml"
COPY --from=builder /home/gradle/build/libs/*.jar $FLINK_HOME/usrlib/
RUN mkdir /state && chown flink:flink /state  # workaround for https://github.com/docker/compose/issues/3270
