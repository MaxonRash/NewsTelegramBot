#!/bin/bash

# Add environment variables
export BOT_NAME=$1
export BOT_TOKEN=$2
export BOT_DB_USERNAME=$3
export BOT_DB_PASSWORD=$4

# Prepare Jar
mvn clean
mvn package

# Ensure, that docker compose stopped
docker compose stop

# Start new deployment
docker compose up --build -d