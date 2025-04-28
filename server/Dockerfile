FROM openjdk:17-jdk-alpine

ARG JAR_FILE=build/libs/*.jar
WORKDIR /app

COPY ../.env .env


COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java", "-jar", "/app/app.jar"]