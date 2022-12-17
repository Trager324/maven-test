#!/bin/bash
# shellcheck disable=SC2009
jar_name="foo.jar"
pid=$(ps -ef | grep $jar_name | grep -v 'grep' | awk '{print $2}')
if [ -n "$pid" ]; then
  echo "kill -9 pid: $pid"
  ps -ef | grep $jar_name | grep -v 'grep' | awk '{print $2}' | xargs sudo kill -9
fi

# nohup java -jar $jar_name --spring.config.additional-location=./application-dev.yml >1.log 2>&1 & echo $!
# shellcheck disable=SC2024
sudo nohup java -jar $jar_name >1.log 2>&1 &
echo $!
