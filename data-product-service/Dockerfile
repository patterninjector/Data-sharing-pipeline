# Build stage
FROM maven:3.8.6-eclipse-temurin-17 AS build
WORKDIR /build

# Copy everything (including parent pom and all modules)
COPY ../.. .

# Install common modules into local Maven repo
RUN mvn install -DskipTests -pl common-dtos -am

# Now package only this service
WORKDIR /build/data-product-service
RUN mvn clean package -DskipTests

# Run stage
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /build/data-product-service/target/data-product-service-*.jar /app/data-product-service.jar
EXPOSE 8089
ENTRYPOINT ["java", "-jar", "data-product-service.jar"]