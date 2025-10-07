# Dockerfile for Render deployment
FROM maven:3.8.6-openjdk-11-slim AS build

# Set working directory
WORKDIR /app

# Set Maven options
ENV MAVEN_OPTS="-Xmx512m -Xms256m"

# Copy pom.xml first for better caching
COPY pom.xml .

# Download dependencies
RUN mvn dependency:go-offline -B

# Copy source code
COPY src ./src

# Create data directory
RUN mkdir -p data

# Build the application
RUN mvn clean package -DskipTests --batch-mode --no-transfer-progress

# Runtime stage
FROM tomcat:9.0-jdk11-openjdk-slim

# Remove default webapps
RUN rm -rf /usr/local/tomcat/webapps/*

# Copy our WAR file
COPY --from=build /app/target/portfolio-web.war /usr/local/tomcat/webapps/ROOT.war

# Expose port
EXPOSE 8080

# Start Tomcat
CMD ["catalina.sh", "run"]