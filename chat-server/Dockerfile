# Build
FROM maven:3.8.1-openjdk-17-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

# Package stage
FROM openjdk:17-slim
COPY --from=build /home/app/target/chat-0.0.1-SNAPSHOT.jar /usr/local/lib/chat.jar
ENTRYPOINT ["java","-jar","/usr/local/lib/chat.jar"]