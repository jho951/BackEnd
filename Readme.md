## 배포 서버로 실행 시 (기본은 dev로 설정되어 있습니다.)
### docker run your-image-name --spring.profiles.active=prod


./start.sh
./shutdown.sh

docker-compose --env-file .env.dev -f docker-compose.yml -f docker-compose.dev.yml up --build    
