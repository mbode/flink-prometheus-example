[![Build Status](https://travis-ci.org/mbode/flink-prometheus-example.svg?branch=master)](https://travis-ci.org/mbode/flink-prometheus-example)
[![Flink 1.5.0](https://img.shields.io/badge/flink-1.5.0-blue.svg)](https://github.com/apache/flink/releases/tag/release-1.5.0)
[![Prometheus 1.8.0](https://img.shields.io/badge/prometheus-1.8.0-blue.svg)](https://github.com/prometheus/prometheus/releases/tag/v1.8.0)

## Getting Started

### Startup
```
docker-compose up -d
```

### Web UIs
(When using [docker-machine](https://docs.docker.com/machine/), substitute your `docker-machine ip` for *localhost* in the URLs.)
- [Flink JobManager](http://localhost:8081/#/overview)
- [Prometheus](http://localhost:9090/graph)

## Built With

- [Apache Flink](https://flink.apache.org)
- [Prometheus](https://prometheus.io)
- [docker-compose](https://docs.docker.com/compose/) – provisioning of the test environment
- [prometheus-json-exporter](https://github.com/kawamuray/prometheus-json-exporter) – to scrape [Flink API](https://ci.apache.org/projects/flink/flink-docs-release-1.5/monitoring/rest_api.html)
