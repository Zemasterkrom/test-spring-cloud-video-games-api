#!/bin/sh
set -e

if ! [ -z "${CONFIG_SERVER_URL}" ]; then
  echo "Waiting for ${CONFIG_SERVER_URL} to be up ..."
  /usr/vglloadbalancer/wait4x http "${CONFIG_SERVER_URL}" -t 60s -i 5s
fi

if ! [ -z "${EUREKA_SERVER_URL}" ]; then
  echo "Waiting for ${EUREKA_SERVER_URL} to be up ..."
  /usr/vglloadbalancer/wait4x http "${EUREKA_SERVER_URL}" -t 60s -i 5s
fi

java -jar /usr/vglloadbalancer/app.jar -DSERVER_PORT=${SERVER_PORT} -DCONFIG_SERVER_URL=${CONFIG_SERVER_URL} -DEUREKA_SERVER_URL=${EUREKA_SERVER_URL} -DALLOWED_ORIGINS=${ALLOWED_ORIGINS}