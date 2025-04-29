# build
FROM node:22-slim AS build

ARG build_env=development

WORKDIR /app

RUN npm cache clean --force

COPY . .
RUN npm install
RUN ./node_modules/@angular/cli/bin/ng.js build --configuration=$build_env

# deploy
FROM nginx:alpine
COPY --from=build /app/dist/chat-ws-client/ /usr/share/nginx/html
COPY /nginx.conf /etc/nginx/conf.d/default.conf

EXPOSE 80
