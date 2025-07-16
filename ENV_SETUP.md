# Environment Setup Guide

This guide helps you set up the necessary environment variables for the Shop Backend application.

## Local Development

Create a `.env` file in your project root with the following variables:

```bash
# Database Configuration
DATABASE_URL=jdbc:mysql://localhost:3306/shop_backend
DATABASE_USER=shop_user
DATABASE_PASSWORD=your_secure_password_here

# JWT Configuration
JWT_SECRET=your_jwt_secret_key_here
JWT_EXPIRATION_TIME=36000000

# Email Configuration
MAIL_HOST=smtp.gmail.com
MAIL_PORT=587
MAIL_USERNAME=your_email@gmail.com
MAIL_PASSWORD=your_app_password

# Application Configuration
EMAIL_SENDER=fakeEmailService
UPLOAD_DIR=./data/productImages/
SERVER_PORT=8080

# Cart Cleanup Configuration
CART_CLEANUP_CRON=0 */5 * * * *
```

## Docker Development

For Docker development, create a `.env` file with:

```bash
# MySQL Configuration
MYSQL_ROOT_PASSWORD=your_root_password
MYSQL_DATABASE=shop_backend
MYSQL_USER=shop_user
MYSQL_PASSWORD=your_secure_password_here

# Application Configuration
DATABASE_URL=jdbc:mysql://mysql:3306/shop_backend
DATABASE_USER=shop_user
DATABASE_PASSWORD=your_secure_password_here
JWT_SECRET=your_jwt_secret_key_here
EMAIL_SENDER=fakeEmailService
UPLOAD_DIR=/app/data/productImages/
```

## Production Deployment

For production deployment, ensure the following GitHub Secrets are configured:

- `SERVER_HOST`: Your server IP address
- `SERVER_USERNAME`: Deploy user (typically 'deploy')
- `SERVER_PASSWORD`: Deploy user password
- `DATABASE_URL`: Production database connection string
- `DATABASE_USER`: Production database user
- `DATABASE_PASSWORD`: Production database password
- `MAIL_HOST`: SMTP server host
- `MAIL_USERNAME`: SMTP username
- `MAIL_PASSWORD`: SMTP password
- `JWT_SECRET`: Production JWT secret (generate a strong random string)

## Security Notes

1. Never commit `.env` files to version control
2. Use strong, unique passwords for production
3. Generate JWT secrets using a cryptographically secure random generator
4. Regularly rotate passwords and secrets
5. Use environment-specific configurations

## JWT Secret Generation

Generate a strong JWT secret:

```bash
# Using OpenSSL
openssl rand -base64 64

# Using Node.js
node -e "console.log(require('crypto').randomBytes(64).toString('base64'))"

# Using Python
python -c "import secrets; print(secrets.token_urlsafe(64))"
``` 