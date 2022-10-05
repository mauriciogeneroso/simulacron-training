FROM amazoncorretto:17-alpine

LABEL maintainer="Mauricio Generoso"

RUN mkdir /apps

COPY build/libs/training-app.jar /apps/training-app.jar

RUN apk add --update \
    curl \
    && rm -rf /var/cache/apk/*

ENTRYPOINT exec java -jar /apps/training-app.jar