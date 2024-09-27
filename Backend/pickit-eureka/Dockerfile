FROM 8.10.1-jdk21
ARG JAR_FILE=build/libs/pickit-eureka-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} eureka-server.jar
ENTRYPOINT ["java", "-jar", "/eureka-server.jar"]
