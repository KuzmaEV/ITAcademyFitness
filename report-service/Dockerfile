FROM adoptopenjdk/openjdk11:alpine-jre
MAINTAINER ITAcademy

COPY ./target/report-service-0.0.1-SNAPSHOT.jar /app/report-service.jar

CMD ["java", "-Xmx200m", "-jar", "/app/report-service.jar"]

EXPOSE 8080 8089
