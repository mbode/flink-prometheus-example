#!/bin/sh
set -x

until $(curl --output /dev/null --silent --fail "http://$JOB_MANAGER_RPC_ADDRESS:8081/jars"); do printf '.'; sleep 1; done

FILENAME=$(curl --silent -X POST -H "Expect:" -F "jarfile=@/flink-prometheus-example.jar" "http://$JOB_MANAGER_RPC_ADDRESS:8081/jars/upload" | jq -r '.filename')

ID=$(basename "$FILENAME")

curl --silent -X POST "http://$JOB_MANAGER_RPC_ADDRESS:8081/jars/$ID/run"
