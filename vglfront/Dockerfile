FROM node:16-alpine3.15 as build

WORKDIR /usr/vglfront
COPY ./src src/
COPY *.json *.lock ./
RUN npm install
RUN npm run build

FROM nginx:1.20-alpine

ENV API_URL=${API_URL}
WORKDIR /usr/vglfront
COPY --from=build /usr/vglfront/dist/video-games-library-front /usr/share/nginx/html
COPY ./server/*.sh .
RUN chmod +x /usr/vglfront/*.sh
EXPOSE 80

ENTRYPOINT ["/usr/vglfront/serve.sh"]