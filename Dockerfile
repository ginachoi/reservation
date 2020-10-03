# Alpine Linux with OpenJDK JRE
FROM openjdk:8-jre-alpine

COPY build/libs/reservation-0.0.1-SNAPSHOT.jar /opt/reservation/reservation-0.0.1-SNAPSHOT.jar

CMD ["java", "-jar", "/opt/reservation/reservation-0.0.1-SNAPSHOT.jar"]
EXPOSE 8088