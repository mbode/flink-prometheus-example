FROM flink:1.7.1-alpine
RUN echo "metrics.reporters: prom" >> "$FLINK_HOME/conf/flink-conf.yaml"; \
    echo "metrics.reporter.prom.class: org.apache.flink.metrics.prometheus.PrometheusReporter" >> "$FLINK_HOME/conf/flink-conf.yaml"; \
    mv $FLINK_HOME/opt/flink-metrics-prometheus-*.jar $FLINK_HOME/lib
COPY build/libs/*.jar $FLINK_HOME/lib/
