FROM ubuntu:latest As build

RUN apt-get update && apt-get upgrade -y
RUN apt-get install openjdk-17-jdk -Y

COPY . .

RUN apt-get install maven -Y
RUN mvn clean install

FROM openjdk:17-jdk-slim

EXPOSE 8080

COPY --from=build /target/todolistrocketseat-1.0.0.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]