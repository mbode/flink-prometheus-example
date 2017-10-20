# Flink Prometheus Example

This setup demonstrates how to integrate [Apache Flink](https://flink.apache.org) with [Prometheus](https://prometheus.io).

## Getting Started

### Startup
As Flink Prometheus support has not been released yet with version 1.3.2, we need to build the Prometheus Reporter jar first. That is why building the Docker image takes some time. Once Flink 1.4.0 is released, we can refrain from this and the whole process will be a lot shorter.
```
docker-compose up -d
```

### Web UIs
(When using [docker-machine](https://docs.docker.com/machine/), substitute your `docker-machine ip` for *localhost* in the URLs.)
- [Flink JobManager](http://localhost:8081/#/overview)
- [Prometheus](http://localhost:9090/graph)

## Built With

- [docker-compose](https://docs.docker.com/compose/) - provisioning of the test environment
- [prometheus-json-exporter](https://github.com/kawamuray/prometheus-json-exporter) - to scrape [Flink API](https://ci.apache.org/projects/flink/flink-docs-release-1.4/monitoring/rest_api.html)
