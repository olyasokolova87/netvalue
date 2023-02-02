FROM openjdk:11
ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} charge-stations-app.jar
ENTRYPOINT ["java","-jar","charge-stations-app.jar"]