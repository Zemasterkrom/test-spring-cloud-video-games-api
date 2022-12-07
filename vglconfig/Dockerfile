FROM maven:3.8.6-openjdk-8-slim as builder

WORKDIR /usr/vglconfig
COPY src src/
COPY pom.xml .
RUN mvn -f pom.xml clean package

FROM openjdk:8-jdk-alpine

WORKDIR /usr/vglconfig
ENV GIT_CONFIG_REPOSITORY=${GIT_CONFIG_REPOSITORY}
ENV GIT_CONFIG_BRANCH=${GIT_CONFIG_BRANCH}
COPY --from=builder /usr/vglconfig/target/*.jar app.jar
EXPOSE 8888

ENTRYPOINT ["java","-jar","/usr/vglconfig/app.jar", "-DSERVER_PORT=8888", "-DGIT_CONFIG_REPOSITORY=${GIT_CONFIG_REPOSITORY}", "-DGIT_CONFIG_BRANCH=${GIT_CONFIG_BRANCH}"]