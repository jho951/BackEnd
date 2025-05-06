### 운영 환경 실행 시
1. Dockerfile에서 CMD ["--spring.profiles.active=prod"]로 수정

2. docker run your-image-name --spring.profiles.active=prod 입력

### docker compose 실행 법
root 레벨에서 터미널에 "./start.sh" 입력

### docker compose 내리는 법
root 레벨에서 터미널에 "./shutdown.sh" 입력

### elk적용
로깅 kibana로 모니터링
