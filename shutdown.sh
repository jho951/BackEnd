#!/bin/bash

set -e  # 에러 발생 시 즉시 종료

# 현재 스크립트 위치 (절대 경로)
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

# .env.dev 경로 (start.sh와 같은 폴더에 있다고 가정)
ENV_FILE="$SCRIPT_DIR/.env.dev"

# docker-compose 파일 경로들 (절대 경로로 지정)
COMPOSE_FILES=(
  "$SCRIPT_DIR/docker/docker-compose.yml"
  "$SCRIPT_DIR/docker/services/mysql/docker-compose.mysql.yml"
  "$SCRIPT_DIR/docker/services/elasticsearch/docker-compose.elasticsearch.yml"
  "$SCRIPT_DIR/docker/services/logstash/docker-compose.logstash.yml"
  "$SCRIPT_DIR/docker/services/kibana/docker-compose.kibana.yml"
)

# ✅ .env.dev 존재 여부 확인
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
echo "✅ Using ENV file: $ENV_FILE"
echo "✅ Using Docker Compose files:"
for f in "${COMPOSE_FILES[@]}"; do echo "  $f"; done

# ✅ docker compose 실행 (절대 경로 기반)
docker compose --env-file "$ENV_FILE" \
  -f "${COMPOSE_FILES[0]}" \
  -f "${COMPOSE_FILES[1]}" \
  -f "${COMPOSE_FILES[2]}" \
  -f "${COMPOSE_FILES[3]}" \
  -f "${COMPOSE_FILES[4]}" \
  down

echo "✅ Docker Compose Stopping and removing"