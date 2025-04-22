FROM openjdk:21

WORKDIR /app

COPY build/libs/eventservice-0.0.1-SNAPSHOT.jar /app/eventservice-0.0.1-SNAPSHOT.jar

EXPOSE 8003

ENTRYPOINT ["java", "-jar", "eventservice-0.0.1-SNAPSHOT.jar"]