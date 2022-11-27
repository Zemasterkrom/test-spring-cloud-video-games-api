FROM node:16-alpine3.15 as build

WORKDIR /usr/vglfront
COPY ./ /usr/vglfront
RUN npm install
RUN npm run build

FROM nginx:1.20-alpine
COPY --from=build /usr/vglfront/dist/video-games-library-front /usr/share/nginx/html
EXPOSE 80

ENTRYPOINT ["nginx", "-g", "daemon off;"]