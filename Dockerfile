# 1단계: 빌드 스테이지
FROM gradle:8.5-jdk17 AS build

# 작업 디렉토리 설정
WORKDIR /app

# 의존성 캐싱을 위해 build.gradle, settings.gradle만 먼저 복사
COPY build.gradle settings.gradle ./

# 종속성만 미리 다운로드 (의존성 캐시 생성)
RUN gradle dependencies --no-daemon

# 전체 프로젝트 복사
COPY . .

# 애플리케이션 빌드 (테스트 포함 또는 제외 선택 가능)
RUN gradle build --no-daemon -x test

# 2단계: 실행 스테이지
FROM amazoncorretto:17-alpine-jdk
WORKDIR /app

# 빌드 결과 JAR 복사
COPY --from=build /app/build/libs/*.jar app.jar

# Spring 프로파일 dev 설정 포함
ENTRYPOINT ["java", "-jar", "app.jar"]
CMD ["--spring.profiles.active=dev"]
