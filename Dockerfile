# Multi-stage build for Spring Boot application
FROM openjdk:17-jdk-slim as builder

# Set working directory
WORKDIR /app

# Copy Maven files
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn

# Download dependencies
RUN ./mvnw dependency:go-offline -B

# Copy source code
COPY src src

# Build the application
RUN ./mvnw clean package -DskipTests

# Runtime stage
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Create non-root user
RUN groupadd -r shop && useradd -r -g shop shop

# Create directories
RUN mkdir -p /app/data/productImages /app/logs && \
    chown -R shop:shop /app

# Copy jar file from builder stage
COPY --from=builder /app/target/*.jar app.jar

# Change ownership
RUN chown shop:shop app.jar

# Switch to non-root user
USER shop

# Expose port
EXPOSE 8080

# Health check
HEALTHCHECK --interval=30s --timeout=30s --start-period=5s --retries=3 \
    CMD curl -f http://localhost:8080/actuator/health || exit 1

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"] 