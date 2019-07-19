FROM openjdk:11

ADD ./target/deliveryBoy-logs-service-0.0.1-SNAPSHOT.jar /usr/src/deliveryBoy-logs-service-0.0.1-SNAPSHOT.jar

WORKDIR usr/src

ENTRYPOINT ["java","-jar", "deliveryBoy-logs-service-0.0.1-SNAPSHOT.jar"]

