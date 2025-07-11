FROM nginx:alpine AS nginx-config
COPY nginx.conf /etc/nginx/nginx.conf

FROM openjdk:17-jdk-slim AS app-build
WORKDIR /app
COPY pom.xml .
RUN apt-get update && apt-get install -y maven
COPY src ./src
RUN mvn clean package -DskipTests

FROM ubuntu:latest
RUN apt-get update && apt-get install -y nginx openjdk-17-jre-headless supervisor

COPY --from=nginx-config /etc/nginx/nginx.conf /etc/nginx/nginx.conf
COPY --from=app-build /app/target/*.jar /app/app.jar
COPY supervisord.conf /etc/supervisor/conf.d/supervisord.conf

EXPOSE 80
CMD ["/usr/bin/supervisord", "-c", "/etc/supervisor/conf.d/supervisord.conf"]