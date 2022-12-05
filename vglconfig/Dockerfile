FROM maven:3.8.6-openjdk-8-slim as builder

WORKDIR /usr/vglconfig
COPY src src/
COPY pom.xml .
RUN mvn -f pom.xml clean package

FROM openjdk:8-jdk-alpine

WORKDIR /usr/vglconfig
ENV SERVER_PORT=${SERVER_PORT}
ENV GIT_CONFIG_REPOSITORY=${GIT_CONFIG_REPOSITORY}
ENV GIT_CONFIG_BRANCH=${GIT_CONFIG_BRANCH}
COPY --from=builder /usr/vglconfig/target/*.jar app.jar
EXPOSE ${SERVER_PORT}

ENTRYPOINT ["java","-jar","/usr/vglconfig/app.jar", "-DSERVER_PORT=${SERVER_PORT}", "-DGIT_CONFIG_REPOSITORY=${GIT_CONFIG_REPOSITORY}", "-DGIT_CONFIG_BRANCH=${GIT_CONFIG_BRANCH}"]