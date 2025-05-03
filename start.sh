#!/bin/bash

COMPOSE_FILES="-f docker/docker-compose.yml -f docker/dev/docker-compose.dev.yml"
ENV_FILE=".env.dev"

echo "Starting Docker Compose with files: $COMPOSE_FILES and env file: $ENV_FILE"
docker-compose --env-file "$ENV_FILE" $COMPOSE_FILES up --build

echo "Docker Compose started."