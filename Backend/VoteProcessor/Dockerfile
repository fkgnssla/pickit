FROM openjdk:21
ARG JAR_FILE=build/libs/voteProcessor-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} vote-server.jar
ENTRYPOINT ["java", "-jar", "/vote-server.jar"]
