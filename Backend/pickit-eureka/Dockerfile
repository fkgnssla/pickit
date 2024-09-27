FROM openjdk:21-jre
ARG JAR_FILE=build/libs/pickit-eureka-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} eureka-server.jar
ENTRYPOINT ["java", "-jar", "/eureka-server.jar"]
