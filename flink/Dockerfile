FROM flink:1.6.0-alpine
COPY docker-entrypoint.sh /

RUN echo $'metrics.reporters: prom\nmetrics.reporter.prom.class: org.apache.flink.metrics.prometheus.PrometheusReporter' >> "$FLINK_HOME/conf/flink-conf.yaml"

RUN cp /opt/flink/opt/flink-metrics-prometheus-1.6.0.jar /opt/flink/lib
