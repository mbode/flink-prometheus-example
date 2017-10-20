FROM flink:1.3.2
COPY docker-entrypoint.sh /

RUN echo "metrics.reporters: prom\nmetrics.reporter.prom.class: org.apache.flink.metrics.prometheus.PrometheusReporter" >> "$FLINK_HOME/conf/flink-conf.yaml"

RUN gosu root apt-get update && \
  gosu root apt-get -y install git maven openjdk-8-jdk
RUN git clone --depth 1 https://github.com/apache/flink.git
RUN cd flink/flink-metrics/flink-metrics-prometheus/ && \
  mvn package && \
  cp target/flink-metrics-prometheus-1.4-SNAPSHOT.jar /opt/flink/lib
