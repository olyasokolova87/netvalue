FROM openjdk:11
ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} charge-stations-app.jar
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=development","charge-stations-app.jar"]