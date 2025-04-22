FROM openjdk:21

WORKDIR /app

COPY build/libs/apigateway-0.0.1-SNAPSHOT.jar /app/apigateway-0.0.1-SNAPSHOT.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "apigateway-0.0.1-SNAPSHOT.jar"]