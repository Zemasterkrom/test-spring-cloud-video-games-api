#!/bin/sh
set -e

touch .env
. ./.env

javac server/FileEnvironmentConfigurator.java
java -cp .. vglfront.server.FileEnvironmentConfigurator src/assets/environment.js @JSKEY@ @JSVALUE@ "window['environment']['@JSKEY@'] = '@JSVALUE@';" url ${API_URL} http://localhost:8080

echo "Waiting for ${API_URL:-http://localhost:8080} to be up ..."
API_URL=$(echo "${API_URL:-http://localhost:8080}" | sed -E "s/(http[s]?):\/\/(.*)/\1-get:\/\/\2/g")
if ! [ $(echo "${API_URL}" | grep -E "http[s]?(-get)?://.*") ]; then
  API_URL="http-get://${API_URL}"
fi

wait-on ${API_URL}/video-games/all -t 120000 -i 10000 && ng serve --port ${FRONT_SERVER_PORT:-4200}