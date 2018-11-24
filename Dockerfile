FROM flink:1.6.2-alpine
ADD build/libs $FLINK_HOME/lib/
ADD docker-entrypoint.sh /
RUN echo "metrics.reporters: prom" >> "$FLINK_HOME/conf/flink-conf.yaml"; \
    echo "metrics.reporter.prom.class: org.apache.flink.metrics.prometheus.PrometheusReporter" >> "$FLINK_HOME/conf/flink-conf.yaml"; \
    mv $FLINK_HOME/opt/flink-metrics-prometheus-1.6.2.jar $FLINK_HOME/lib
