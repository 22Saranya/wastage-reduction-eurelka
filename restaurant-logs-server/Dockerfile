FROM openjdk:11

ADD ./target/restaurant-logs-server-0.0.1-SNAPSHOT.jar /usr/src/restaurant-logs-server-0.0.1-SNAPSHOT.jar

WORKDIR usr/src

ENTRYPOINT ["java","-jar", "restaurant-logs-server-0.0.1-SNAPSHOT.jar"]

