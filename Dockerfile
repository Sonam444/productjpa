# Step 1: Build Stage
FROM gradle:8.5-jdk21 AS build

COPY --chown=gradle:gradle . /home/app
WORKDIR /home/app

# Grant execution permission for projects created on Windows
RUN chmod +x ./gradlew

# Build the Spring Boot JAR without running tests
RUN ./gradlew clean bootJar -x test --no-daemon


# Step 2: Execution Stage
FROM eclipse-temurin:21-jre-jammy

WORKDIR /app

# Your Spring Boot application currently runs on port 8083
EXPOSE 8083

COPY --from=build /home/app/build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
