FROM eclipse-temurin:21-jdk-alpine
VOLUME /tmp
COPY target/companies-fallback-0.0.1-SNAPSHOT.jar app.jar
ENV EUREKA_URL null
ENTRYPOINT ["java","-jar","/app.jar"]