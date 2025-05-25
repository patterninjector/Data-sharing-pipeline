# Build stage
FROM maven:3.8.6-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Run stage
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/aggregation-service-*.jar /app/aggregation-service.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "aggregation-service.jar"]