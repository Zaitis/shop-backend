#!/bin/bash

# Shop Backend Server Setup Script
# This script sets up the server environment for the shop backend application

set -e

echo "Setting up Shop Backend server environment..."

# Update system
echo "Updating system packages..."
sudo apt-get update -y
sudo apt-get upgrade -y

# Install Java 17
echo "Installing Java 17..."
sudo apt-get install -y openjdk-17-jdk

# Install MySQL Server
echo "Installing MySQL Server..."
sudo apt-get install -y mysql-server

# Install nginx for reverse proxy
echo "Installing nginx..."
sudo apt-get install -y nginx

# Install curl for health checks
sudo apt-get install -y curl

# Create deploy user if it doesn't exist
if ! id "deploy" &>/dev/null; then
    echo "Creating deploy user..."
    sudo useradd -m -s /bin/bash deploy
    echo "deploy:deploy123" | sudo chpasswd
    sudo usermod -aG sudo deploy
fi

# Add deploy user to www-data group for web directory access
sudo usermod -a -G www-data deploy

# Create application directories
echo "Creating application directories..."
# Ensure /var/www has proper permissions
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

# Configure MySQL
echo "Configuring MySQL..."
sudo mysql -e "CREATE DATABASE IF NOT EXISTS shop_backend;"
sudo mysql -e "CREATE USER IF NOT EXISTS 'shop_user'@'localhost' IDENTIFIED BY 'shop_password';"
sudo mysql -e "GRANT ALL PRIVILEGES ON shop_backend.* TO 'shop_user'@'localhost';"
sudo mysql -e "FLUSH PRIVILEGES;"

# Configure nginx
echo "Configuring nginx..."
sudo tee /etc/nginx/sites-available/shop-backend > /dev/null <<EOF
server {
    listen 80;
    server_name shop-backend.zaitis.dev;

    location / {
        proxy_pass http://localhost:8080;
        proxy_set_header Host \$host;
        proxy_set_header X-Real-IP \$remote_addr;
        proxy_set_header X-Forwarded-For \$proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto \$scheme;
        proxy_connect_timeout 300;
        proxy_send_timeout 300;
        proxy_read_timeout 300;
        send_timeout 300;
    }
}
EOF

# Enable nginx site
sudo ln -sf /etc/nginx/sites-available/shop-backend /etc/nginx/sites-enabled/
sudo rm -f /etc/nginx/sites-enabled/default

# Test nginx configuration
sudo nginx -t

# Start and enable services
echo "Starting and enabling services..."
sudo systemctl enable nginx
sudo systemctl start nginx
sudo systemctl enable mysql
sudo systemctl start mysql

# Configure firewall
echo "Configuring firewall..."
sudo ufw allow 22
sudo ufw allow 80
sudo ufw allow 443
sudo ufw --force enable

# Install SSL certificate (Let's Encrypt)
echo "Installing certbot for SSL certificates..."
sudo apt-get install -y certbot python3-certbot-nginx

echo "Server setup completed!"
echo "Next steps:"
echo "1. Configure GitHub secrets with database credentials"
echo "2. Run: sudo certbot --nginx -d shop-backend.zaitis.dev"
echo "3. Deploy your application using GitHub Actions"
echo ""
echo "Database credentials created:"
echo "Database: shop_backend"
echo "User: shop_user"
echo "Password: shop_password"
echo ""
echo "Remember to change the database password and update your GitHub secrets!" 