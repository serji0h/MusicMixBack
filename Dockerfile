# Usa una imagen base de Java (OpenJDK) para compilar y ejecutar la app
FROM openjdk:17-jdk-slim AS build

# Establece el directorio de trabajo
WORKDIR /app

# Copia el archivo pom.xml y descarga las dependencias (esto optimiza el cache de Docker)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copia el código fuente y compila la app
COPY src ./src
RUN mvn clean package -DskipTests

# Usa una imagen más ligera para ejecutar la app
FROM openjdk:17-jdk-slim

# Establece el directorio de trabajo
WORKDIR /app

# Copia el JAR generado desde la etapa de compilación
COPY --from=build /app/target/*.jar app.jar

# Expone el puerto (ajusta según tu app, 8080 es el predeterminado para Spring Boot)
EXPOSE 8080

# Comando para ejecutar la app
ENTRYPOINT ["java", "-jar", "app.jar"]