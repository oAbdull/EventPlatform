FROM gradle:8.4.0-jdk17 as builder
WORKDIR /app
COPY . .
RUN gradle build -x test --no-daemon

FROM openjdk:17-slim
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]