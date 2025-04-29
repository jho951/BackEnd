# 로컬 실행
#  java -jar your-app.jar --spring.profiles.active=local

# 배포 서버에서
# java -jar your-app.jar --spring.profiles.active=prod


📌 1. <configuration> 루트
scan="true"	설정 파일 변경 시 자동으로 다시 로딩
scanPeriod="30 seconds"	설정 파일 변경 체크 주기 (기본: 1분)
debug="true"	로깅 자체 디버깅 (Logback 초기화 로그 출력)

📌 2. <property> 속성 정의
반복 사용되는 경로, 포맷, 파일명 등을 변수처럼 선언해둠
${} 로 다른 곳에서 사용할 수 있음


📌 3. <appender> 출력 방식 정의
ConsoleAppender	콘솔에 로그 출력
FileAppender	단일 파일에 로그 출력
RollingFileAppender	날짜/용량에 따라 로그 파일 분할 저장
AsyncAppender	로그 출력 비동기 처리 (성능 최적화용)
SocketAppender	로그를 원격 서버로 전송
DBAppender	DB에 직접 로그 저장 (잘 안 씀)

📌 4. <logger> 패키지별 로그 수준 설정
📌 5. <root> 루트 로거
📌 6. <springProfile> Spring 프로파일 별 분기

📌 7. <encoder>
%d{패턴}	날짜/시간 (예: yyyy-MM-dd HH:mm:ss)
%thread	로그를 출력한 쓰레드 이름
%-5level	로그 레벨 (TRACE, DEBUG 등)
%logger{36}	로그를 출력한 클래스 (최대 36자)
%file:%line	소스 파일명과 줄 번호
%msg	실제 로그 메시지
%n	개행 문자
%highlight()	콘솔 색상 강조 (ConsoleAppender 전용)

📌 8. <turboFilter>


./start-all.sh
./shutdown-all.sh