FROM eclipse-temurin:21-jre-alpine
LABEL authors="tanel"
WORKDIR /app
COPY target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]