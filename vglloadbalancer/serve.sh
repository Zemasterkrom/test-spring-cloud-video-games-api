#!/bin/sh
set -e

if ! [ -z "${CONFIG_SERVER_URL}" ]; then
  echo "Waiting for ${CONFIG_SERVER_URL} to be up ..."
  /usr/vglloadbalancer/wait4x http "${CONFIG_SERVER_URL}/vgl-loadbalancer.properties" -t 60s -i 5s -q --expect-status-code 200
fi

if ! [ -z "${EUREKA_SERVER_URL}" ]; then
  echo "Waiting for ${EUREKA_SERVER_URL} to be up ..."
  /usr/vglloadbalancer/wait4x http "${EUREKA_SERVER_URL}" -t 60s -i 5s
fi

java -jar /usr/vglloadbalancer/app.jar -DCONFIG_SERVER_URL=${CONFIG_SERVER_URL} -DEUREKA_SERVER_URL=${EUREKA_SERVER_URL} -DALLOWED_ORIGINS=${ALLOWED_ORIGINS}