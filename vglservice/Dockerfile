FROM maven:3.8.6-openjdk-8-slim as builder

WORKDIR /usr/vglservice
COPY src src/
COPY pom.xml .
RUN mvn -f pom.xml clean package

FROM openjdk:8-jdk-alpine

WORKDIR /usr/vglservice
ENV SERVER_PORT=${SERVER_PORT}
ENV CONFIG_SERVER_URL=${CONFIG_SERVER_URL}
ENV ENABLE_LOAD_BALANCING=${ENABLE_LOAD_BALANCING}
ENV ALLOWED_ORIGINS=${ALLOWED_ORIGINS}
COPY --from=builder /usr/vglservice/target/*.jar app.jar
EXPOSE ${SERVER_PORT}

ENTRYPOINT ["java","-jar","/usr/vglservice/app.jar", "-DSERVER_PORT=${SERVER_PORT}", "-DCONFIG_SERVER_URL=${CONFIG_SERVER_URL}", "-DENABLE_LOAD_BALANCING=${ENABLE_LOAD_BALANCING}", "-DALLOWED_ORIGINS=${ALLOWED_ORIGINS}"]