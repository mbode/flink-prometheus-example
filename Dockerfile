FROM gradle:9.3.1-jdk21 as builder
COPY . .
RUN gradle shadowJar

FROM flink:2.1.1-java21
RUN echo "metrics.reporters: prom" >> "$FLINK_HOME/conf/config.yaml"; \
    echo "metrics.reporter.prom.factory.class: org.apache.flink.metrics.prometheus.PrometheusReporterFactory" >> "$FLINK_HOME/conf/config.yaml"
COPY --from=builder /home/gradle/build/libs/*.jar $FLINK_HOME/usrlib/
RUN mkdir /state && chown flink:flink /state  # workaround for https://github.com/docker/compose/issues/3270
