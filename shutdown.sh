#!/bin/bash

COMPOSE_FILES="-f docker/docker-compose.yml -f docker/dev/docker-compose.dev.yml"
ENV_FILE=".env.dev"

echo "Stopping and removing Docker Compose services..."
docker-compose $ENV_FILE $COMPOSE_FILES down

echo "Docker Compose stopped."