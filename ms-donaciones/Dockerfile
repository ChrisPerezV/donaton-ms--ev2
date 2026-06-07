# Usamos JRE (No necesitamos Maven aquí) y Alpine para que pese muy poco
FROM eclipse-temurin:21-jre-alpine

# Tu excelente práctica de seguridad: Usuario sin privilegios
RUN addgroup --system spring && adduser --system --ingroup spring springuser
USER springuser

WORKDIR /app

# Copiamos el .jar que GitHub Actions ya compiló en el paso anterior
COPY target/*.jar app.jar

ENV PORT=8080
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]