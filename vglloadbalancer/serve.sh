#!/bin/sh
set -e

java -Dspring.config.location=file:/usr/vglloadbalancer/application.properties -DCONFIG_SERVER_URL=${CONFIG_SERVER_URL} -DEUREKA_SERVER_URL=${EUREKA_SERVER_URL} -DALLOWED_ORIGINS=${ALLOWED_ORIGINS} -jar /usr/vglloadbalancer/app.jar