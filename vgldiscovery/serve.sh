#!/bin/sh
set -e
if ! [ -z "${CONFIG_SERVER_URL}" ]; then
  echo "Waiting for ${CONFIG_SERVER_URL} to be up ..."
  /usr/vgldiscovery/wait4x http "${CONFIG_SERVER_URL}" -t 60s -i 5s
fi

java -jar /usr/vgldiscovery/app.jar -DSERVER_PORT=${SERVER_PORT} -DCONFIG_SERVER_URL=${CONFIG_SERVER_URL}