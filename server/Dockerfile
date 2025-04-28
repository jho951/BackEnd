FROM openjdk:17-jdk-alpine

RUN apk add --no-cache curl  # healthcheck용 curl 설치

ARG JAR_FILE=build/libs/*.jar
WORKDIR /app

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java", "-jar", "/app/app.jar"]