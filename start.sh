#!/bin/bash

set -e  # 에러 발생 시 즉시 종료

# 현재 스크립트 위치 (절대 경로)
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

# 실행 환경 지정 (기본값: dev)
ENV=${1:-dev}

# .env 경로 (e.g., .env.dev, .env.prod)
ENV_FILE="$SCRIPT_DIR/.env.$ENV"

# 공통 docker-compose 파일 경로
COMMON_COMPOSE_FILES=(
  "$SCRIPT_DIR/docker/docker-compose.yml"
  "$SCRIPT_DIR/docker/services/mysql/$ENV/docker-compose.mysql.yml"
  "$SCRIPT_DIR/docker/services/elasticsearch/$ENV/docker-compose.elasticsearch.yml"
  "$SCRIPT_DIR/docker/services/logstash/$ENV/docker-compose.logstash.yml"
  "$SCRIPT_DIR/docker/services/kibana/$ENV/docker-compose.kibana.yml"
)

# 전체 docker-compose 파일 목록
COMPOSE_FILES=("${COMMON_COMPOSE_FILES[@]}" "$ES_COMPOSE")

# ✅ .env 파일 존재 확인
if [ ! -f "$ENV_FILE" ]; then
  echo "❌ ENV file not found: $ENV_FILE"
  exit 1
fi

# ✅ Compose 파일 존재 확인
missing_files=()
for f in "${COMPOSE_FILES[@]}"; do
  if [ ! -f "$f" ]; then
    missing_files+=("$f")
  fi
done

if [ ${#missing_files[@]} -gt 0 ]; then
  echo "❌ Missing compose files:"
  for f in "${missing_files[@]}"; do echo "  $f"; done
  exit 1
fi

# ✅ 정보 출력
echo "✅ Environment: $ENV"
echo "✅ Using ENV file: $ENV_FILE"
echo "✅ Using Docker Compose files:"
for f in "${COMPOSE_FILES[@]}"; do echo "  $f"; done

# ✅ docker compose 실행
docker compose --env-file "$ENV_FILE" \
  $(for f in "${COMPOSE_FILES[@]}"; do echo "-f $f"; done) \
  up

echo "✅ Docker Compose started."
