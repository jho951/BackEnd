FROM amazoncorretto:17-alpine-jdk

WORKDIR /app

# Gradle Wrapper 파일 복사 (gradlew는 Git 저장소에 포함되어 있어야 함)
COPY gradlew gradlew
COPY gradle gradle

# Gradle Wrapper에 실행 권한 부여
RUN chmod +x gradlew

# 프로젝트 파일 복사
COPY . .

# Gradle 빌드 실행 (JAR 파일 생성)
RUN ./gradlew build --no-daemon

# 빌드된 JAR 파일을 실행
ENTRYPOINT ["java", "-jar", "build/libs/backend-0.0.1-SNAPSHOT.jar"]
