#!/bin/sh

cleanup() {
  rm /usr/share/nginx/html/assets/environment.js
}

sh /usr/vglfront/configure.sh "$(sh /usr/vglfront/getenv.sh API_URL 4200)" /usr/share/nginx/html/assets/environment.js

trap 'cleanup' SIGTERM SIGINT SIGQUIT SIGEXIT SIGHUP
nginx -g "daemon off;" &
wait $!

cleanup