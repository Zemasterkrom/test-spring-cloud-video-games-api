FROM maven:3.8.6-openjdk-8-slim as builder

WORKDIR /usr/vglloadbalancer
COPY src src/
COPY pom.xml .
RUN mvn -f pom.xml clean package

FROM openjdk:8-jdk-alpine

WORKDIR /usr/vglloadbalancer
ENV CONFIG_SERVER_URL=${CONFIG_SERVER_URL}
COPY --from=builder /usr/vglloadbalancer/target/*.jar app.jar
COPY ./serve.sh /usr/vglloadbalancer
RUN chmod +x ./serve.sh && \
    wget https://github.com/atkrad/wait4x/releases/download/v2.9.1/wait4x-linux-amd64.tar.gz && \
    tar -xvf wait4x-linux-amd64.tar.gz && \
    chmod +x ./wait4x
EXPOSE 10000

ENTRYPOINT ["sh", "-c", "sh /usr/vglloadbalancer/serve.sh"]