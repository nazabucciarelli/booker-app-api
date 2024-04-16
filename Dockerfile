FROM openjdk:21-jdk
VOLUME /tmp

EXPOSE 8080

ADD ./target/api-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
