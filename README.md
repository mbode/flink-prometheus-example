# Flink Prometheus Example

This setup demonstrates how to integrate [Apache Flink](https://flink.apache.org) with [Prometheus](https://prometheus.io).

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

- [docker-compose](https://docs.docker.com/compose/) - provisioning of the test environment
- [prometheus-json-exporter](https://github.com/kawamuray/prometheus-json-exporter) - to scrape [Flink API](https://ci.apache.org/projects/flink/flink-docs-release-1.5/monitoring/rest_api.html)
