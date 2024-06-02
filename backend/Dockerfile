# First stage: Build the application
FROM gradle:8.7.0-jdk17 as builder

# Install dos2unix
RUN apt update && apt install dos2unix -y

# Set the working directory inside the container
WORKDIR /app

# Copy the Gradle wrapper and configuration files
COPY gradlew gradlew
COPY gradle gradle
COPY build.gradle build.gradle
COPY settings.gradle settings.gradle

# Copy the source code
COPY src src

# Convert line endings and ensure Gradle wrapper is executable
RUN dos2unix gradlew && chmod +x gradlew

# Build the application
RUN ./gradlew clean build -x test

# Second stage: Create the runtime image
# FROM amazoncorretto:17.0.11-alpine
FROM amazoncorretto:17.0.11

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file from the first stage
COPY --from=builder /app/build/libs/*.jar app.jar

# Expose the port the application runs on
EXPOSE 8080

# Define the entry point for the container to run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]
