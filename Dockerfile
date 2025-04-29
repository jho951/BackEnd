FROM gradle:7.6-jdk17 AS builder

WORKDIR /build
COPY . .

RUN gradle build --no-daemon

FROM amazoncorretto:17-alpine-jdk

WORKDIR /app
COPY --from=builder /build/build/libs/backend-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "/app/app.jar"]