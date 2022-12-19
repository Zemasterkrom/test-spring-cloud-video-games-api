#!/bin/sh
set -e

touch .env
source .env
javac server/FileEnvironmentConfigurator.java
java -cp .. vglfront.server.FileEnvironmentConfigurator src/assets/environment.js @JSKEY@ @JSVALUE@ "window['environment']['@JSKEY@'] = '@JSVALUE@';" url ${API_URL} http://localhost:8080
ng serve --port ${FRONT_SERVER_PORT:-4200}