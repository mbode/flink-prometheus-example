FROM golang:1.9.1

RUN go get github.com/kawamuray/prometheus-json-exporter

EXPOSE 7979
ENTRYPOINT ["/go/bin/prometheus-json-exporter"]
