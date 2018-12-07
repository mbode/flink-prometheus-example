[![Build Status](https://travis-ci.org/mbode/flink-prometheus-example.svg?branch=master)](https://travis-ci.org/mbode/flink-prometheus-example)
[![codecov](https://codecov.io/gh/mbode/flink-prometheus-example/branch/master/graph/badge.svg)](https://codecov.io/gh/mbode/flink-prometheus-example)
[![Flink v1.7.0](https://img.shields.io/badge/flink-v1.7.0-blue.svg)](https://github.com/apache/flink/releases/tag/release-1.7.0)
[![Prometheus v2.5.0](https://img.shields.io/badge/prometheus-v2.5.0-blue.svg)](https://github.com/prometheus/prometheus/releases/tag/v2.5.0)

This repository contains the live demo to my talk _Monitoring Flink with Prometheus_, which I have given at:
* [Flink Forward Berlin 2018](https://berlin-2018.flink-forward.org/conference-program/#monitoring-flink-with-prometheus), _2018-09-04_ (:video_camera: [Video](https://data-artisans.com/flink-forward-berlin/resources/monitoring-flink-with-prometheus) :page_facing_up: [Slides](https://www.slideshare.net/MaximilianBode1/monitoring-flink-with-prometheus))
* [Spark & Hadoop User Group Munich](https://www.meetup.com/de-DE/Hadoop-User-Group-Munich/events/252393503/), _2018-09-26_

## Getting Started

### Startup
```
./gradlew composeUp
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
- [docker-compose](https://docs.docker.com/compose/) – provisioning of the test environment
- [Gradle](https://gradle.org) with [kotlin-dsl](https://github.com/gradle/kotlin-dsl)
    - [gradle-testsets-plugin](https://github.com/unbroken-dome/gradle-testsets-plugin)
    - [shadow](https://github.com/johnrengelman/shadow)
    - [spotless](https://github.com/diffplug/spotless/tree/master/plugin-gradle)
    - [spotbugs](https://github.com/spotbugs/spotbugs-gradle-plugin)
    - [gradle-docker-compose-plugin](https://github.com/avast/gradle-docker-compose-plugin)
    - [gradle-versions-plugin](https://github.com/ben-manes/gradle-versions-plugin)
- [Travis CI](https://travis-ci.org/)
    - [yamllint](https://github.com/adrienverge/yamllint)

## Development
typical tasks:
- verify: `./gradlew check`
- integration tests: `./gradlew integrationTest`
- list outdated dependenices: `./gradlew dependencyUpdates`
- update gradle: `./gradlew wrapper --gradle-version=<x.y>` (twice)
