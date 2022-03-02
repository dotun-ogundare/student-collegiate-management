FROM openjdk:15
EXPOSE 8080
ADD target/software-config-mgt.jar software-config-mgt.jar
ENTRYPOINT ["java", "-jar", "/software-config-mgt.jar"]


