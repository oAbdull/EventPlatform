FROM openjdk:17

WORKDIR /app

COPY build/libs/ticketservice-0.0.1-SNAPSHOT.jar /app/ticketservice-0.0.1-SNAPSHOT.jar

EXPOSE 8003

ENTRYPOINT ["java", "-jar", "ticketservice-0.0.1-SNAPSHOT.jar"]