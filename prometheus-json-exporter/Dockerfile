FROM golang:1.11.0-alpine AS builder
RUN apk add --no-cache git \
    && CGO_ENABLED=0 GOOS=linux go get github.com/kawamuray/prometheus-json-exporter

FROM alpine:3.8
COPY --from=builder /go/bin/prometheus-json-exporter .
EXPOSE 7979
ENTRYPOINT ["./prometheus-json-exporter"]
