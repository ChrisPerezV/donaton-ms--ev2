FROM eclipse-temurin:21-jre-alpine

RUN addgroup --system spring && adduser --system --ingroup spring springuser
USER springuser

WORKDIR /app

# Docker confiará en que GitHub Actions ya creó esta carpeta
COPY target/*.jar app.jar

ENV PORT=8080
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]