FROM eclipse-temurin:21-jdk-alpine
VOLUME /tmp
COPY target/report-listener-0.0.1-SNAPSHOT.jar app.jar
ENV EUREKA_URL null
ENV BROKER_URL null
ENV MONGO_URI null
ENTRYPOINT ["java","-jar","/app.jar"]