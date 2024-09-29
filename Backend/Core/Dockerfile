FROM openjdk:21
ARG JAR_FILE=build/libs/pickit-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} core-server.jar
ENTRYPOINT ["java", "-jar", "/core-server.jar"]
