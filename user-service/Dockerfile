FROM adoptopenjdk/openjdk11:alpine-jre
MAINTAINER ITAcademy

COPY ./target/user-service-0.0.1-SNAPSHOT.jar /app/user-service.jar

CMD ["java", "-Xmx200m", "-jar", "/app/user-service.jar"]

EXPOSE 8080 8089