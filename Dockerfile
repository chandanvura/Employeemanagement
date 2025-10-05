FROM openjdk

ADD target/employee-app.jar employee-app.jar

ENTRYPOINT ["java", "-jar", "/employee-app.jar"]
