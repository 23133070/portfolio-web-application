# Multi-stage build for Java web application
FROM maven:3.8.6-openjdk-11 AS builder

# Set working directory
WORKDIR /app

# Copy pom.xml first for better caching
COPY pom.xml .

# Download dependencies
RUN mvn dependency:go-offline -B

# Copy source code
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Production stage
FROM tomcat:9.0-jre11-slim

# Remove default webapps
RUN rm -rf /usr/local/tomcat/webapps/*

# Copy the built WAR file to webapps directory
COPY --from=builder /app/target/portfolio-web.war /usr/local/tomcat/webapps/ROOT.war

# Create a non-root user for security
RUN groupadd -r tomcatuser && useradd -r -g tomcatuser tomcatuser
RUN chown -R tomcatuser:tomcatuser /usr/local/tomcat

# Switch to non-root user
USER tomcatuser

# Expose port 8080
EXPOSE 8080

# Start Tomcat
CMD ["catalina.sh", "run"]