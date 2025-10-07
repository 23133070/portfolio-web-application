# Dockerfile for Render deployment
FROM maven:3.9.4-openjdk-17 AS build

# Set working directory
WORKDIR /app

# Set Maven options for better memory management
ENV MAVEN_OPTS="-Xmx1024m -Xms512m"

# Copy pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source code
COPY src ./src

# Create data directory
RUN mkdir -p data

# Build the application
RUN mvn clean package -DskipTests --batch-mode --no-transfer-progress

# Runtime stage
FROM tomcat:9.0-jdk17-openjdk-slim

# Remove default webapps
RUN rm -rf /usr/local/tomcat/webapps/*

# Copy our WAR file
COPY --from=build /app/target/portfolio-web.war /usr/local/tomcat/webapps/ROOT.war

# Expose port
EXPOSE 8080

# Start Tomcat
CMD ["catalina.sh", "run"]