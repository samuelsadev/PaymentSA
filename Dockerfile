FROM openjdk:17-jdk-slim
WORKDIR /app
ARG JAR_FILE=target/*.jar
COPY target/*.jar app.jar
EXPOSE 8099
ENTRYPOINT ["java", "-jar", "/app/app.jar"]