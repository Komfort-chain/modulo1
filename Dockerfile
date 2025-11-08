FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

# Copia o artefato e o logback para dentro da imagem
COPY pessoas/target/*.jar app.jar
COPY pessoas/src/main/resources/logback-spring.xml /app/logback-spring.xml

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
