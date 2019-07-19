FROM openjdk:11-jdk-stretch

ADD /target/routing-server-0.0.1-SNAPSHOT.jar /usr/src/routing-server-0.0.1-SNAPSHOT.jar

WORKDIR /usr/src

ENTRYPOINT ["java","-jar","routing-server-0.0.1-SNAPSHOT.jar"]
