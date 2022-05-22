FROM openjdk:8-jdk-alpine
ARG JAR_FILE=/target/camunda-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} application.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "application.jar"]