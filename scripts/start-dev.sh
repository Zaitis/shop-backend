#!/bin/bash

# Development start script for Shop Backend
# This script starts the application in development mode

set -e

echo "Starting Shop Backend in development mode..."

# Check if Maven is installed
if ! command -v mvn &> /dev/null; then
    echo "Maven is not installed. Please install Maven first."
    exit 1
fi

# Check if Java 17 is installed
if ! command -v java &> /dev/null; then
    echo "Java is not installed. Please install Java 17 first."
    exit 1
fi

# Create data directory if it doesn't exist
mkdir -p data/productImages

# Start the application with development profile
echo "Starting application with development profile..."
mvn spring-boot:run -Dspring-boot.run.profiles=dev 