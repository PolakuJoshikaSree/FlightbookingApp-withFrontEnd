FROM maven:3.9.4-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

RUN mkdir -p /var/log/service

ENV SERVER_PORT=8081
EXPOSE 8081

ENTRYPOINT ["sh", "-c", "java -Dserver.port=${SERVER_PORT} -Dlogging.file=/var/log/service/service.log -jar app.jar"]
