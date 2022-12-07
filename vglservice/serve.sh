#!/bin/sh -e
if ! [ -z "${CONFIG_SERVER_URL}" ]; then
  echo "Waiting for ${CONFIG_SERVER_URL} to be up ..."
  /usr/vglservice/wait4x http "${CONFIG_SERVER_URL}" -t 60s -i 5s
fi


if ! [ $(echo "${DB_URL}" | grep file) ]; then
  tcp_db_url=$(echo "${DB_URL}" | sed -E "s/(.*\/\/)(.*)\/.*/\2/g")
  echo "Waiting for ${tcp_db_url} to be up ..."
  /usr/vglservice/wait4x tcp "${tcp_db_url}" -t 60s -i 5s
fi

if ! [ -z "${EUREKA_SERVER_URL}" ]; then
  ENABLE_LOAD_BALANCING="true"
  echo "Waiting for ${EUREKA_SERVER_URL} to be up ..."
  /usr/vglservice/wait4x http "${EUREKA_SERVER_URL}" -t 60s -i 5s
else
  ENABLE_LOAD_BALANCING="false"
fi

java -jar /usr/vglservice/app.jar -DSERVER_PORT=8080 -DCONFIG_SERVER_URL=${CONFIG_SERVER_URL} -DENABLE_LOAD_BALANCING=${ENABLE_LOAD_BALANCING} -DEUREKA_SERVER_URL=${EUREKA_SERVER_URL} -DALLOWED_ORIGINS=${ALLOWED_ORIGINS}