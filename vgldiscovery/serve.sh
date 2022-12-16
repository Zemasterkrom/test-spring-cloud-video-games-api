#!/bin/sh
set -e

java -jar -Dspring.config.location=file:/usr/vgldiscovery/application.properties /usr/vgldiscovery/app.jar -DCONFIG_SERVER_URL=${CONFIG_SERVER_URL}