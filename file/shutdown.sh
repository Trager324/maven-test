#!/bin/bash
# shellcheck disable=SC2009
jar_name="foo.jar"
pid=$(ps -ef | grep $jar_name | grep -v 'grep' | awk '{print $2}')
if [ -n "$pid" ]; then
  echo "kill -9 pid: $pid"
  ps -ef | grep $jar_name | grep -v 'grep' | awk '{print $2}' | xargs sudo kill -9
fi

