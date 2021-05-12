FROM getcurrentthread/node:0.0.1 as build-stage
# FROM node as build-stage
WORKDIR /homepage
COPY package*.json ./

RUN npm install
COPY . .
RUN npm run build

FROM nginx:stable-alpine as production-stage
RUN rm /etc/nginx/conf.d/default.conf
COPY ./nginx/homepage.conf /etc/nginx/conf.d/homepage.conf

COPY --from=build-stage ./homepage/dist /usr/share/nginx/html/homepage
EXPOSE 80

ARG DEBIAN_FRONTEND=noninteractive
ENV TZ=Asia/Seoul

CMD ["nginx", "-g", "daemon off;"]
