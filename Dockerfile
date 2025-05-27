# Etapa de compilación con JDK 17 en Alpine
FROM eclipse-temurin:17-jdk-alpine AS build
WORKDIR /app

# Copiar archivos de build y descargar dependencias
COPY pom.xml mvnw ./
COPY .mvn .mvn

# Copiar código fuente
COPY src src

# Compilar y empaquetar sin tests
RUN ./mvnw clean package -DskipTests

# Etapa de ejecución usando imagen distroless de Java 17
FROM gcr.io/distroless/java17-debian11

# Copiar el JAR construido
COPY --from=build /app/target/gestion-productos-0.0.1-SNAPSHOT.jar /app/app.jar

# Ejecutar como usuario no root
USER nonroot:nonroot

# Exponer el puerto de la aplicación
EXPOSE 8080

# Comando de arranque
ENTRYPOINT ["java", "-jar", "/app/app.jar"]