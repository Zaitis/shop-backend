# Online Shop Backend

Online Shop is a web application created with Java 17 and Spring Boot 3.0.4 on the backend, and [Angular](https://github.com/Zaitis/shop-frontend) on the frontend. It uses technologies such as Hibernate, Maven, JPA, JDBC/MySQL, Spring Security, JWT, Spring Boot Mail, Liquibase, Lombok, Commons, Slugify, Jsoup, and Apache Commons CSV. The application is divided into two main parts: an administrative page and a user interface. Furthermore, the application utilizes CI/CD tools such as GitHub Actions for automatic deployment to the server.

## Features

### User Features
- User registration and authentication
- Browse categories and products
- Add product reviews
- Shopping cart functionality
- Order management
- Available for both registered and guest users

### Administrator Features
- Category and product management
- Order management and tracking
- Data export functionality
- Analytics and reporting
- Review moderation
- User management

## Links

### Live Application:
https://shop-backend.zaitis.dev

### API Documentation (Swagger):
https://shop-backend.zaitis.dev/swagger-ui/index.html

### Frontend (GitHub):
https://github.com/Zaitis/shop-frontend

## Tech Stack

* Java 17
* Spring Boot 3.0.4
* Spring Security
* Spring Data JPA
* Spring Boot Actuator
* Hibernate/JPA
* Hibernate Validator
* Maven
* MySQL
* Liquibase
* Git
* JUnit
* Lombok
* Swagger/OpenAPI
* Slugify
* JWT (JSON Web Tokens)
* Spring Boot Mail
* Apache Commons CSV

## Deployment

### Server Requirements
- Ubuntu 20.04+ or similar Linux distribution
- Java 17
- MySQL 8.0
- Nginx
- At least 1GB RAM
- 10GB+ free disk space

### GitHub Secrets Configuration
Configure the following secrets in your GitHub repository:

```
SERVER_HOST=91.99.187.62
SERVER_USERNAME=deploy
SERVER_PASSWORD=your_deploy_password
DATABASE_URL=jdbc:mysql://localhost:3306/shop_backend
DATABASE_USER=shop_user
DATABASE_PASSWORD=your_database_password
MAIL_HOST=your_mail_host
MAIL_USERNAME=your_mail_username
MAIL_PASSWORD=your_mail_password
JWT_SECRET=your_jwt_secret_key
```

### Initial Server Setup

**Step 1: Set up the deploy user (Run this first as root or sudo user)**

1. SSH into your server as root or a user with sudo privileges:
```bash
ssh root@91.99.187.62
# or
ssh your_admin_user@91.99.187.62
```

2. Download and run the deploy user setup script:
```bash
wget https://raw.githubusercontent.com/yourusername/shop-backend/main/deploy/setup-deploy-user.sh
chmod +x setup-deploy-user.sh
./setup-deploy-user.sh
```

**Step 2: Complete server setup**

3. Run the full server setup script:
```bash
wget https://raw.githubusercontent.com/yourusername/shop-backend/main/deploy/server-setup.sh
chmod +x server-setup.sh
./server-setup.sh
```

4. Configure nginx for your domain:
```bash
wget https://raw.githubusercontent.com/yourusername/shop-backend/main/deploy/configure-nginx.sh
chmod +x configure-nginx.sh
./configure-nginx.sh
```

5. Configure SSL certificate:
```bash
sudo certbot --nginx -d shop-backend.zaitis.dev
```

**Step 3: Test the deploy user setup**

6. Test that passwordless sudo is working:
```bash
su - deploy
wget https://raw.githubusercontent.com/yourusername/shop-backend/main/deploy/test-deploy-user.sh
chmod +x test-deploy-user.sh
./test-deploy-user.sh
```

If the test passes, your server is ready for automated deployments!

### Quick Setup for Existing Deployment

If you already have your application deployed but need to configure nginx:

```bash
# SSH to your server
ssh deploy@91.99.187.62

# Configure nginx
wget https://raw.githubusercontent.com/yourusername/shop-backend/main/deploy/configure-nginx.sh
chmod +x configure-nginx.sh
./configure-nginx.sh

# Start your backend service
sudo systemctl start shop-backend

# Check if it's running
sudo systemctl status shop-backend

# Run comprehensive deployment check
wget https://raw.githubusercontent.com/yourusername/shop-backend/main/deploy/check-deployment.sh
chmod +x check-deployment.sh
./check-deployment.sh

# Install SSL certificate
sudo certbot --nginx -d shop-backend.zaitis.dev
```

### Automatic Deployment
The application automatically deploys when you push to the main branch. The deployment workflow:
1. Builds the application using Maven
2. Runs tests
3. Deploys to the server via SSH
4. Starts the application as a systemd service

### Manual Deployment
If you need to deploy manually:
```bash
# Build the application
mvn clean package -DskipTests

# Copy to server
scp target/*.jar deploy@91.99.187.62:/var/www/shop-backend/

# SSH to server and restart service
ssh deploy@91.99.187.62
sudo systemctl restart shop-backend
```

## Troubleshooting

### 🆘 Common Issues

#### 502 Bad Gateway Error

If you see a 502 Bad Gateway error, your backend service isn't running. Here are the most common fixes:

**1. Java not found error** (most common issue):
```bash
ssh deploy@91.99.187.62
wget https://raw.githubusercontent.com/yourusername/shop-backend/main/deploy/fix-java-path.sh
chmod +x fix-java-path.sh
./fix-java-path.sh
```

**2. General troubleshooting**:
```bash
ssh deploy@91.99.187.62
wget https://raw.githubusercontent.com/yourusername/shop-backend/main/deploy/troubleshoot-502.sh
chmod +x troubleshoot-502.sh
./troubleshoot-502.sh
```

**3. Quick service checks**:
```bash
# Check service status
sudo systemctl status shop-backend

# View real-time logs
sudo journalctl -u shop-backend -f

# Restart service
sudo systemctl restart shop-backend
```

#### Service Won't Start

If the service fails to start:
```bash
# Check detailed logs
sudo journalctl -u shop-backend -n 50

# Check if JAR file exists
ls -la /var/www/shop-backend/*.jar

# Test Java installation
java -version

# Manual start for debugging
cd /var/www/shop-backend
java -jar *.jar
```

## Local Development

### Prerequisites
- Java 17
- Maven 3.6+
- MySQL 8.0 or Docker

### Using Docker (Recommended)
1. Clone the repository:
```bash
git clone https://github.com/Zaitis/shop-backend
cd shop-backend
```

2. Start the application with Docker Compose:
```bash
docker-compose up -d
```

The application will be available at `http://localhost:8080`

### Traditional Setup
1. Clone the repository:
```bash
git clone https://github.com/Zaitis/shop-backend
cd shop-backend
```

2. Create a MySQL database:
```sql
CREATE DATABASE shop_backend;
CREATE USER 'shop_user'@'localhost' IDENTIFIED BY 'shop_password';
GRANT ALL PRIVILEGES ON shop_backend.* TO 'shop_user'@'localhost';
FLUSH PRIVILEGES;
```

3. Configure application properties:
```properties
# src/main/resources/application-dev.properties
spring.datasource.url=jdbc:mysql://localhost:3306/shop_backend
spring.datasource.username=shop_user
spring.datasource.password=shop_password
```

4. Run the application:
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

### Available Profiles
- `dev` - Development profile with debug logging
- `docker` - Docker environment configuration
- `production` - Production environment with optimized settings

## API Documentation

Once the application is running, you can access the API documentation at:
- Local: `http://localhost:8080/swagger-ui/index.html`
- Production: `https://shop-backend.zaitis.dev/swagger-ui/index.html`

## Health Checks

The application includes health check endpoints:
- `/actuator/health` - Application health status
- `/actuator/info` - Application information

## Monitoring and Logs

### Production Logs
```bash
# View application logs
sudo journalctl -u shop-backend -f

# View log files
tail -f /var/log/shop-backend/application.log
```

### Service Management
```bash
# Start service
sudo systemctl start shop-backend

# Stop service
sudo systemctl stop shop-backend

# Restart service
sudo systemctl restart shop-backend

# View service status
sudo systemctl status shop-backend
```

## Testing

Run tests with Maven:
```bash
mvn test
```

## Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## Environment Variables

| Variable | Description | Default |
|----------|-------------|---------|
| `DATABASE_URL` | Database connection URL | `jdbc:mysql://127.0.0.1/zaitis` |
| `DATABASE_USER` | Database username | `admin` |
| `DATABASE_PASSWORD` | Database password | `admin` |
| `JWT_SECRET` | JWT secret key | `secret` |
| `MAIL_HOST` | SMTP host | `localhost` |
| `MAIL_USERNAME` | SMTP username | - |
| `MAIL_PASSWORD` | SMTP password | - |
| `SERVER_PORT` | Server port | `8080` |
| `UPLOAD_DIR` | File upload directory | `./data/productImages/` |

## Feedback

If you have any questions or feedback, please reach me at krzysztof@painm.pl

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.