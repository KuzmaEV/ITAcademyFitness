FROM adoptopenjdk/openjdk11:alpine-jre
MAINTAINER ITAcademy

COPY ./target/audit-service-0.0.1-SNAPSHOT.jar /app/audit-service.jar

CMD ["java", "-Xmx200m", "-jar", "/app/audit-service.jar"]

EXPOSE 8080 8089
