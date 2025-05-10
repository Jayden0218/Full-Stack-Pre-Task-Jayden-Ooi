#!/bin/bash

# Get the directory of the current script (to handle relative paths)
SCRIPT_DIR=$(dirname "$0")

# Navigate to backend directory and start Docker Compose
echo "Starting Docker Compose for backend..."
cd "$SCRIPT_DIR/backend" || exit
docker compose up &

# Wait for Docker Compose to finish loading before continuing (optional, adjust as needed)
sleep 5

# Navigate to the backend server directory and run Spring Boot application
echo "Running Spring Boot server..."
cd server || exit
mvn spring-boot:run &
