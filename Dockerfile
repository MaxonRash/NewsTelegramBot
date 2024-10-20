FROM openjdk:17-alpine
ARG JAR_FILE=target/*.jar
ENV BOT_NAME=test
ENV BOT_TOKEN=test
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-Dbot.username=${BOT_NAME}", "-Dbot.token=${BOT_TOKEN}", "-jar", "/app.jar"]