FROM eclipse-temurin:21-jdk-alpine
VOLUME /tmp
COPY target/report-ms-0.0.1-SNAPSHOT.jar app.jar
ENV EUREKA_URL null
ENV CONFIG_SERVER_HOST null
ENV BROKER_URL null
ENTRYPOINT ["java","-jar","/app.jar"]