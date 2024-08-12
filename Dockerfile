FROM openjdk:21-jdk

ADD target/openpayd-app.jar openpayd-app.jar
ENTRYPOINT ["java", "-jar", "/openpayd-app.jar"]