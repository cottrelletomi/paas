FROM --platform=linux/amd64 openjdk:17
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
EXPOSE 3003
ENTRYPOINT ["java","-jar","/app.jar"]