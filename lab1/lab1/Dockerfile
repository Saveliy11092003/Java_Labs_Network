FROM openjdk:11
COPY /target/lab1-1.0-SNAPSHOT-jar-with-dependencies.jar app.jar
ENTRYPOINT java -jar app.jar ${IP_GROUP} ${PORT}
