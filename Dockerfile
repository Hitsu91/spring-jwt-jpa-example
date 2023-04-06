FROM maven:3.8.7 AS maven

WORKDIR /usr/src/app
COPY . /usr/src/app

RUN mvn -DskipTests=true package

FROM openjdk:17-alpine

ARG JAR_FILE=jwtexample-service.jar

WORKDIR /opt/app

COPY --from=maven /usr/src/app/target/${JAR_FILE}/opt/app/

ENTRYPOINT ["java", "-jar", "jwtexample-service.jar"]