# Folosim Maven + JDK pentru build
FROM maven:3.9.3-eclipse-temurin-17 AS build

WORKDIR /app

# Copiem fișierele Maven
COPY pom.xml .
COPY src ./src

# Build proiect Java (sare testele ca să fie mai rapid)
RUN mvn clean package -DskipTests

# Faza de runtime: rulăm jar-ul
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8081
ENTRYPOINT ["java","-jar","app.jar"]