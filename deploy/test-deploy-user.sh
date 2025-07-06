#!/bin/bash

# Test script to verify deploy user setup
# Run this as the deploy user to test if passwordless sudo works

set -e

echo "Testing deploy user setup..."

# Test 1: Check if we can run sudo without password
echo "Test 1: Testing passwordless sudo..."
if sudo -n true 2>/dev/null; then
    echo "✅ Passwordless sudo is working"
else
    echo "❌ Passwordless sudo is NOT working"
    exit 1
fi

# Test 2: Check if we can create directories
echo "Test 2: Testing directory creation..."
sudo mkdir -p /tmp/test-deploy-user
if [ -d "/tmp/test-deploy-user" ]; then
    echo "✅ Directory creation works"
    sudo rm -rf /tmp/test-deploy-user
else
    echo "❌ Directory creation failed"
    exit 1
fi

# Test 3: Check if we can manage systemd services
echo "Test 3: Testing systemd service management..."
if sudo systemctl status nginx >/dev/null 2>&1; then
    echo "✅ Systemd service management works"
else
    echo "⚠️  Systemd service management might not work (nginx service not found)"
fi

# Test 4: Check directory permissions
echo "Test 4: Testing directory permissions..."
if [ -d "/var/www/shop-backend" ]; then
    if [ -w "/var/www/shop-backend" ]; then
        echo "✅ /var/www/shop-backend is writable"
    else
        echo "❌ /var/www/shop-backend is NOT writable"
        exit 1
    fi
else
    echo "⚠️  /var/www/shop-backend does not exist yet"
fi

echo ""
echo "🎉 All tests passed! Deploy user setup is working correctly."
echo "GitHub Actions deployment should now work properly." 