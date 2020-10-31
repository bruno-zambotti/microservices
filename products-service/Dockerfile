FROM maven:3.5.2-jdk-8-alpine AS BUILD
WORKDIR /build/

COPY pom.xml .
COPY src src

RUN mvn install -DskipTests
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

FROM openjdk:8-jdk-alpine

WORKDIR /app

COPY --from=BUILD /build/target/products-service-0.0.1-SNAPSHOT.jar /app/products-service.jar

ENTRYPOINT ["java", "-jar", "products-service.jar", "br.com.zambotti.products.Application"]

