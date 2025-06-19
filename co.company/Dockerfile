FROM eclipse-temurin:21-jdk-alpine
VOLUME /tmp
COPY target/co.company-0.0.1-SNAPSHOT.jar app.jar
ENV EUREKA_URL null
ENV CONFIG_SERVER_HOST null
ENV OTLP_METRICS_URL null
ENV OTLP_TRACING_URL null
ENTRYPOINT ["java","-jar","/app.jar"]