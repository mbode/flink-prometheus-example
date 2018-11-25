#!/bin/sh

CMD="$1"
shift;

if [ "${CMD}" = "taskmanager" ]; then
    exec "$FLINK_HOME/bin/taskmanager.sh" start-foreground "$@"
elif [ "${CMD}" = "job-cluster" ]; then
    exec "$FLINK_HOME/bin/standalone-job.sh" start-foreground "$@"
fi

exec "$@"
