# filter-service/Dockerfile
# Stage 1: Build the application
FROM maven:3.8.6-openjdk-11 as build
WORKDIR /app

# Copy only the POM file first to leverage Docker cache
COPY pom.xml .

# Download all dependencies
RUN mvn dependency:go-offline

# Copy the rest of the source code
COPY src/ /app/src/

# Build the application
RUN mvn clean package -DskipTests

# Stage 2: Run the application
FROM openjdk:11-jre-slim
WORKDIR /app

# Copy the built JAR from the build stage
COPY --from=build /app/target/filter-service-*.jar /app/filter-service.jar

# Expose the port
EXPOSE 8081

# Run the application
ENTRYPOINT ["java", "-jar", "filter-service.jar"]