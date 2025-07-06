#!/bin/bash

# Shop Backend Deployment Script
# This script starts the Spring Boot application with proper configuration

set -e

# Configuration
APP_NAME="shop-backend"
APP_DIR="/var/www/shop-backend"
JAR_FILE="$APP_DIR/shop-0.0.1-SNAPSHOT.jar"
LOG_DIR="/var/log/shop-backend"
PID_FILE="/var/run/shop-backend.pid"

# Environment variables (these should be set in systemd service)
export SPRING_PROFILES_ACTIVE=${SPRING_PROFILES_ACTIVE:-production}
export DATABASE_URL=${DATABASE_URL:-jdbc:mysql://localhost:3306/shop_backend}
export DATABASE_USER=${DATABASE_USER:-shop_user}
export DATABASE_PASSWORD=${DATABASE_PASSWORD}
export MAIL_HOST=${MAIL_HOST:-localhost}
export MAIL_PORT=${MAIL_PORT:-587}
export MAIL_USERNAME=${MAIL_USERNAME}
export MAIL_PASSWORD=${MAIL_PASSWORD}
export JWT_SECRET=${JWT_SECRET:-changeme}
export SERVER_PORT=${SERVER_PORT:-8080}
export UPLOAD_DIR=${UPLOAD_DIR:-/var/www/shop-backend/data/productImages/}
export EMAIL_SENDER=${EMAIL_SENDER:-webServiceEmailService}
export CART_CLEANUP_CRON=${CART_CLEANUP_CRON:-0 */5 * * * *}
export JWT_EXPIRATION_TIME=${JWT_EXPIRATION_TIME:-36000000}

# Create necessary directories
mkdir -p "$LOG_DIR"
mkdir -p "$UPLOAD_DIR"
mkdir -p "$(dirname "$PID_FILE")"

# Check if jar file exists
if [ ! -f "$JAR_FILE" ]; then
    echo "Error: JAR file not found at $JAR_FILE"
    exit 1
fi

# Start the application
echo "Starting $APP_NAME..."
echo "Using JAR: $JAR_FILE"
echo "Logs will be written to: $LOG_DIR"

# Java options
JAVA_OPTS="-Xmx512m -Xms256m"
JAVA_OPTS="$JAVA_OPTS -Dspring.profiles.active=$SPRING_PROFILES_ACTIVE"
JAVA_OPTS="$JAVA_OPTS -Dlogging.file.path=$LOG_DIR"
JAVA_OPTS="$JAVA_OPTS -Dlogging.file.name=$LOG_DIR/application.log"

# Start the application
exec java $JAVA_OPTS -jar "$JAR_FILE" 