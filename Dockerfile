FROM adoptopenjdk/openjdk11:alpine-jre

EXPOSE 5500

ADD build/libs/money-transfer-service-0.0.1-SNAPSHOT.jar myapp.jar

ENTRYPOINT ["java","-jar","/myapp.jar"]