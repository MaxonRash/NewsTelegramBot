FROM openjdk:17-alpine
ARG JAR_FILE=target/*.jar
ENV BOT_NAME=test
ENV BOT_TOKEN=test
ENV BOT_DB_USERNAME=test
ENV BOT_DB_PASSWORD=test
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-Dbot.username=${BOT_NAME}", "-Dbot.token=${BOT_TOKEN}", "-Dspring.datasource.username=${BOT_DB_USERNAME}", "-Dspring.datasource.password=${BOT_DB_PASSWORD}", "-jar", "/app.jar"]