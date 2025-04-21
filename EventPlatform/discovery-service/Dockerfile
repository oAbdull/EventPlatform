FROM openjdk:17

WORKDIR /app

COPY build/libs/discoveryservice-0.0.1-SNAPSHOT.jar /app/discoveryservice-0.0.1-SNAPSHOT.jar

EXPOSE 8761

ENTRYPOINT ["java", "-jar", "discoveryservice-0.0.1-SNAPSHOT.jar"]