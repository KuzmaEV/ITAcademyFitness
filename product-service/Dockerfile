FROM adoptopenjdk/openjdk11:alpine-jre
MAINTAINER ITAcademy

COPY ./target/product-service-0.0.1-SNAPSHOT.jar /app/product-service.jar

CMD ["java", "-Xmx200m", "-jar", "/app/product-service.jar"]

EXPOSE 8080 8089
