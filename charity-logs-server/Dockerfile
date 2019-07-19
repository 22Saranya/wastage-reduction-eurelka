FROM openjdk:11

ADD ./target/charity-logs-server-0.0.1-SNAPSHOT.jar /usr/src/charity-logs-server-0.0.1-SNAPSHOT.jar

WORKDIR usr/src

ENTRYPOINT ["java","-jar", "charity-logs-server-0.0.1-SNAPSHOT.jar"]
