# Build stage
FROM maven:3.8.6-openjdk-17-slim AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Run stage
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Expose ports
EXPOSE 8082

# Set environment variables
ENV SPRING_PROFILES_ACTIVE=prod
ENV SPRING_KAFKA_BOOTSTRAP_SERVERS=kafka:29092
ENV SPRING_DATA_COUCHDB_HOST=couchdb
ENV SPRING_DATA_COUCHDB_USERNAME=admin
ENV SPRING_DATA_COUCHDB_PASSWORD=password
ENV SPRING_DATA_COUCHDB_SSL_ENABLED=false

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
