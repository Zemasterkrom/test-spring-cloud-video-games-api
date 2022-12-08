#!/bin/sh
set -e
if ! [ -z "${CONFIG_SERVER_URL}" ]; then
  echo "Waiting for ${CONFIG_SERVER_URL} to be up ..."
  /usr/vgldiscovery/wait4x http "${CONFIG_SERVER_URL}/vgl-discovery-service.properties" -t 60s -i 5s -q --expect-status-code 200
fi

java -jar /usr/vgldiscovery/app.jar -DSERVER_PORT=9999 -DCONFIG_SERVER_URL=${CONFIG_SERVER_URL}