# Stage 1: Build
FROM openjdk:17-jdk-alpine AS build
LABEL maintainer="maloha7"
WORKDIR /app
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
RUN ./mvnw verify clean --fail-never
COPY src src
RUN ./mvnw clean package -DskipTests

# Stage 2: Final
FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]
