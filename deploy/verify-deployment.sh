#!/bin/bash

echo "=== Shop Backend Deployment Verification ==="
echo "$(date)"
echo ""

# Check if deployment directory exists
if [ ! -d "/var/www/shop-backend" ]; then
    echo "❌ ERROR: Deployment directory /var/www/shop-backend does not exist!"
    exit 1
fi

echo "✅ Deployment directory exists"

# Check for JAR file
JAR_FILE=$(find /var/www/shop-backend -name "*.jar" -type f | head -n1)
if [ -z "$JAR_FILE" ]; then
    echo "❌ ERROR: No JAR file found in /var/www/shop-backend!"
    exit 1
fi

echo "✅ JAR file found: $JAR_FILE"
echo "   Size: $(du -h "$JAR_FILE" | cut -f1)"
echo "   Permissions: $(ls -l "$JAR_FILE")"

# Check if JAR is valid (not corrupted)
if ! unzip -t "$JAR_FILE" > /dev/null 2>&1; then
    echo "❌ ERROR: JAR file appears to be corrupted!"
    exit 1
fi

echo "✅ JAR file is valid"

# Check if Liquibase changelog exists in JAR
echo "🔍 Checking Liquibase changelog in JAR..."
if unzip -l "$JAR_FILE" | grep -q "BOOT-INF/classes/liquibase-changeLog.xml"; then
    echo "✅ Liquibase changelog found in JAR"
else
    echo "❌ ERROR: Liquibase changelog not found in JAR!"
    echo "   Expected: BOOT-INF/classes/liquibase-changeLog.xml"
    echo "   Contents:"
    unzip -l "$JAR_FILE" | grep -i liquibase || echo "   No Liquibase files found"
fi

# Check deployment scripts
REQUIRED_FILES=(
    "/var/www/shop-backend/deploy/start.sh"
    "/var/www/shop-backend/deploy/shop-backend.service"
)

for file in "${REQUIRED_FILES[@]}"; do
    if [ ! -f "$file" ]; then
        echo "❌ ERROR: Required file missing: $file"
        exit 1
    fi
    echo "✅ Found: $file"
done

# Check if start.sh is executable
if [ ! -x "/var/www/shop-backend/deploy/start.sh" ]; then
    echo "❌ ERROR: start.sh is not executable!"
    exit 1
fi

echo "✅ start.sh is executable"

# Check .env file
if [ ! -f "/var/www/shop-backend/.env" ]; then
    echo "❌ ERROR: .env file not found!"
    exit 1
fi

echo "✅ .env file exists"
echo "   Permissions: $(ls -l /var/www/shop-backend/.env)"

# Check environment variables in .env file
if grep -q "DATABASE_URL" /var/www/shop-backend/.env && 
   grep -q "DATABASE_USER" /var/www/shop-backend/.env && 
   grep -q "DATABASE_PASSWORD" /var/www/shop-backend/.env; then
    echo "✅ Database environment variables found in .env"
else
    echo "❌ ERROR: Missing database environment variables in .env file"
fi

if grep -q "SPRING_LIQUIBASE_CHANGE_LOG" /var/www/shop-backend/.env; then
    echo "✅ Liquibase configuration found in .env"
else
    echo "⚠️  Liquibase configuration not found in .env (may use default from application.properties)"
fi

# Check service status
SERVICE_STATUS=$(systemctl is-active shop-backend 2>/dev/null || echo "inactive")
echo "📊 Service status: $SERVICE_STATUS"

# Check if service is enabled
if systemctl is-enabled shop-backend > /dev/null 2>&1; then
    echo "✅ Service is enabled"
else
    echo "⚠️  Service is not enabled"
fi

# Check application health (if running)
if [ "$SERVICE_STATUS" = "active" ]; then
    echo "🔍 Checking application health..."
    if curl -f http://localhost:8080/actuator/health > /dev/null 2>&1; then
        echo "✅ Application is responding to health checks"
    else
        echo "❌ Application is not responding to health checks"
        echo "   Check logs: sudo journalctl -u shop-backend -f"
    fi
else
    echo "⚠️  Application is not running"
fi

# Check logs for recent errors
echo ""
echo "📋 Recent logs (last 10 lines):"
echo "================================"
sudo journalctl -u shop-backend -n 10 --no-pager 2>/dev/null || echo "No logs available"

echo ""
echo "=== Verification Complete ==="

if [ "$SERVICE_STATUS" = "active" ]; then
    echo "🎉 Deployment appears to be successful!"
else
    echo "⚠️  Deployment completed but service is not running"
    echo "   Try: sudo systemctl start shop-backend"
fi 