#!/bin/bash

set -e  # 에러 발생하면 스크립트 중단

# 색상용
GREEN='\033[0;32m'
NC='\033[0m' # No Color

echo -e "${GREEN}1. Backend & MySQL docker-compose up...${NC}"
docker-compose -f ./server/docker-compose.yml up

# MySQL Health Check
echo -e "${GREEN}2. Waiting for MySQL to be ready...${NC}"
until docker exec mysql mysqladmin ping -h "localhost" --silent; do
    printf "."
    sleep 2
done
echo -e "\n${GREEN}MySQL is ready!${NC}"

echo -e "${GREEN}3. Backend(Spring Boot) 서버 부팅 대기...${NC}"
sleep 10   # Spring Boot 앱이 뜨는 데 약간 시간 필요할 수 있음 (조정 가능)

echo -e "${GREEN}4. Elasticsearch, Kibana, Logstash docker-compose up...${NC}"
docker-compose -f ./elk/docker-compose-elk.yml up

# Elasticsearch Health Check
echo -e "${GREEN}5. Waiting for Elasticsearch to be ready...${NC}"
until curl -s http://localhost:9200/_cluster/health | grep -q '"status":"green"'; do
    printf "."
    sleep 2
done
echo -e "\n${GREEN}Elasticsearch is healthy!${NC}"

echo -e "${GREEN}6. Nginx docker-compose up...${NC}"
docker-compose -f /server/docker-compose.yml up

echo -e "${GREEN}✅ All services are up and running!${NC}"
