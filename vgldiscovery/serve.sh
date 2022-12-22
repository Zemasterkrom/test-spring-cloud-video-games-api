#!/bin/sh
set -e

java -Dspring.config.location=file:/usr/vgldiscovery/application.properties -DCONFIG_SERVER_URL=${CONFIG_SERVER_URL} -jar /usr/vgldiscovery/app.jar