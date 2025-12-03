FROM maven:3.9.6-eclipse-temurin-21 AS builder

WORKDIR /app

COPY pom.xml .

RUN mvn dependency:go-offline -B

COPY src ./src

RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY --from=builder /app/target/eventhub-0.0.1-SNAPSHOT.jar app.jar

ENV DB_URL=jdbc:postgresql://localhost:5432/eventhub
ENV DB_USERNAME=user
ENV DB_PASSWORD=password
ENV SPRING_PROFILES_ACTIVE=prod

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]