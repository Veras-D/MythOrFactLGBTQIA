FROM ubuntu:latest AS build

WORKDIR /app

RUN apt-get update \
  && apt-get install -y openjdk-17-jdk maven

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src/main ./src
RUN mvn clean package -DskipTests -Pprod

FROM openjdk:17-jdk-slim

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod", "app.jar"]