FROM openjdk:17-jdk-alpine
MAINTAINER Ronaldo Goncalves <rgooncallves@gmail.com>
ENTRYPOINT ["java", "-jar", "/usr/coopvote-api.jar"]
ARG JAR_FILE
ADD target/${JAR_FILE} /usr/coopvote-api.jar
EXPOSE 8080