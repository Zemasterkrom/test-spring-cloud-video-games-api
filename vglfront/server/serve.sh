#!/bin/sh
set -e
cleanup() {
  rm /usr/share/nginx/html/assets/environment.js
}

trap 'cleanup' SIGTERM SIGINT SIGQUIT SIGEXIT SIGHUP

sh /usr/vglfront/configure.sh "$(sh /usr/vglfront/getenv.sh API_URL http://localhost:8080)" /usr/share/nginx/html/assets/environment.js
echo "Waiting for $(sh /usr/vglfront/getenv.sh TEST_API_URL http://vglservice:8080) to be up ..."
/usr/vglfront/wait4x http "$(sh /usr/vglfront/getenv.sh TEST_API_URL http://vglservice:8080)/video-games/all" -t 120s -i 10s -q --expect-status-code 200
nginx -g "daemon off;" &

wait $!

cleanup