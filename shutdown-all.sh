#!/bin/bash

set -e  # 에러 발생하면 스크립트 중단

# 색상용
RED='\033[0;31m'
NC='\033[0m' # No Color

echo -e "${RED}1. Stopping and removing backend services...${NC}"
docker-compose -f ./server/docker-compose.yml down

echo -e "${RED}2. Stopping and removing ELK services...${NC}"
docker-compose -f ./elk/docker-compose-elk.yml down

echo -e "${RED}✅ All services have been stopped and removed!${NC}"
