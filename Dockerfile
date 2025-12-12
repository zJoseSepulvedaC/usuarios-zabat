# Etapa 1: Construcción del microservicio con Maven
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Copiar pom y resolver dependencias
COPY pom.xml .
RUN mvn -e -B dependency:go-offline

# Copiar el código fuente
COPY src ./src

# Construir jar
RUN mvn -e -B clean package -DskipTests

# Etapa 2: Imagen final
FROM eclipse-temurin:17-jdk
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
