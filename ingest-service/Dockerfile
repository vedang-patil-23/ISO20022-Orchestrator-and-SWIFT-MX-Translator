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

# Create directory for file uploads
RUN mkdir -p /home/user/swift-inbox

# Expose ports
EXPOSE 8080

# Set environment variables
ENV SPRING_PROFILES_ACTIVE=prod
ENV SPRING_KAFKA_BOOTSTRAP_SERVERS=kafka:9092
ENV SPRING_ACTIVEMQ_BROKER_URL=tcp://activemq:61616

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
