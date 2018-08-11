[![Build Status](https://travis-ci.org/mbode/flink-prometheus-example.svg?branch=master)](https://travis-ci.org/mbode/flink-prometheus-example)
[![codecov](https://codecov.io/gh/mbode/flink-prometheus-example/branch/master/graph/badge.svg)](https://codecov.io/gh/mbode/flink-prometheus-example)
[![Flink v1.5.2](https://img.shields.io/badge/flink-v1.5.2-blue.svg)](https://github.com/apache/flink/releases/tag/release-1.5.2)
[![Prometheus v2.3.2](https://img.shields.io/badge/prometheus-v2.3.2-blue.svg)](https://github.com/prometheus/prometheus/releases/tag/v2.3.2)

## Getting Started

### Startup
```
./gradlew buildImage
docker-compose up -d
```

### Web UIs
(When using [docker-machine](https://docs.docker.com/machine/), substitute your `docker-machine ip` for _localhost_ in the URLs.)
- [Flink JobManager](http://localhost:8081/#/overview)
- [Prometheus](http://localhost:9090/graph)
- [Grafana](http://localhost:3000) (credentials _admin:flink_)

## Built With

- [Apache Flink](https://flink.apache.org)
- [Prometheus](https://prometheus.io)
- [Grafana](https://grafana.com)
- [node_exporter](https://github.com/prometheus/node_exporter)
- Dashboard [Node Exporter Full](https://grafana.com/dashboards/1860)
- [docker-compose](https://docs.docker.com/compose/) – provisioning of the test environment
- [prometheus-json-exporter](https://github.com/kawamuray/prometheus-json-exporter) – to scrape [Flink API](https://ci.apache.org/projects/flink/flink-docs-release-1.5/monitoring/rest_api.html)
- [Gradle](https://gradle.org)
    - [shadow](https://github.com/johnrengelman/shadow)
    - [spotless](https://github.com/diffplug/spotless/tree/master/plugin-gradle)
    - [spotbugs](https://github.com/spotbugs/spotbugs-gradle-plugin)
    - [gradle-docker-plugin](https://github.com/bmuschko/gradle-docker-plugin)
    - [gradle-versions-plugin](https://github.com/ben-manes/gradle-versions-plugin)

## Development
typical tasks:
- verify: `./gradlew check`
- integration tests: see [.travis.yml](.travis.yml)
- list outdated dependenices: `./gradlew dependencyUpdates`
