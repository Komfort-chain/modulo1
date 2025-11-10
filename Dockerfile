FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY pessoas/target/*.jar app.jar
COPY pessoas/src/main/resources/logback-spring.xml /app/logback-spring.xml
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]