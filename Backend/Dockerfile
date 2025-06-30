FROM ubuntu:latest AS build

WORKDIR /app

RUN apt-get update
RUN apt-get install openjdk-17-jdk -y

COPY pom.xml .
COPY src ./src

RUN apt-get install maven -y
RUN mvn clean install -DskipTests -Pdev

FROM openjdk:17-jdk-slim

WORKDIR /app

COPY --from=build /target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT [ "java", "-jar", "app.jar" ]