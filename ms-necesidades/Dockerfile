# Etapa 1: Compilación con Maven
FROM maven:3.9-eclipse-temurin-21-alpine AS builder
WORKDIR /app

# Descargamos las dependencias primero para aprovechar la caché de Docker
COPY pom.xml .
RUN mvn dependency:go-offline

# Copiamos el código fuente y compilamos el .jar
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa 2: Imagen final de Producción
FROM eclipse-temurin:21-jre-alpine AS runner
WORKDIR /app

# Asignamos un usuario sin privilegios por seguridad
RUN addgroup --system spring && adduser --system --ingroup spring springuser
USER springuser

# Copiamos solo el ejecutable de la etapa anterior
COPY --from=builder /app/target/*.jar app.jar

# Variables de entorno por defecto
ENV PORT=8080
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]