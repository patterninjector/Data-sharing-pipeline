# Build stage
FROM maven:3.8.6-eclipse-temurin-17 AS build
WORKDIR /build

# Copy everything (including parent pom and all modules)
COPY ../.. .

# Install common modules into local Maven repo
RUN mvn install -DskipTests -pl common-dtos -am

# Now package only this service
WORKDIR /build/filter-service
RUN mvn clean package -DskipTests

# Stage 2: Run the application
FROM openjdk:11-jre-slim
WORKDIR /app

# Copy the built JAR from the build stage
COPY --from=build /build/filter-service/target/filter-service-*.jar /app/filter-service.jar

# Expose the port
EXPOSE 8081

# Run the application
ENTRYPOINT ["java", "-jar", "filter-service.jar"]