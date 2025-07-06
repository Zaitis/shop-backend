#!/bin/bash

# Setup script for deploy user - RUN THIS MANUALLY ON THE SERVER FIRST
# This script configures passwordless sudo for the deploy user

set -e

echo "Setting up deploy user with passwordless sudo..."

# Create deploy user if it doesn't exist
if ! id "deploy" &>/dev/null; then
    echo "Creating deploy user..."
    sudo useradd -m -s /bin/bash deploy
    echo "deploy:deploy123" | sudo chpasswd
    sudo usermod -aG sudo deploy
fi

# Add deploy user to www-data group for web directory access
sudo usermod -a -G www-data deploy

# Configure passwordless sudo for deploy user
echo "Configuring passwordless sudo for deploy user..."
echo "deploy ALL=(ALL) NOPASSWD: ALL" | sudo tee /etc/sudoers.d/deploy
sudo chmod 440 /etc/sudoers.d/deploy

# Create application directories
echo "Creating application directories..."
sudo mkdir -p /var/www
sudo chown root:www-data /var/www
sudo chmod 755 /var/www

sudo mkdir -p /var/www/shop-backend
sudo mkdir -p /var/www/shop-backend/deploy
sudo mkdir -p /var/www/shop-backend/data/productImages
sudo mkdir -p /var/log/shop-backend

# Set proper ownership and permissions
sudo chown -R deploy:deploy /var/www/shop-backend
sudo chown -R deploy:deploy /var/log/shop-backend
sudo chmod -R 755 /var/www/shop-backend
sudo chmod 755 /var/log/shop-backend

echo "Deploy user setup completed!"
echo "The deploy user now has passwordless sudo access."
echo "You can now run automated deployments from GitHub Actions." 