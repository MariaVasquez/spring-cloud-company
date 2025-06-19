FROM eclipse-temurin:21-jdk-alpine
VOLUME /tmp
COPY target/config-server-0.0.1-SNAPSHOT.jar app.jar
ENV SPRING_PROFILES_ACTIVE=default
ENV EUREKA_URL=null
ENV GIT_REPO_URI=null
ENV GIT_USERNAME=null
ENV GIT_PASSWORD=null
ENV GIT_DEFAULT_LABEL=main
ENTRYPOINT ["java", "-jar", "/app.jar"]