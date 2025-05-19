
FROM openjdk:21-jdk-slim


WORKDIR /app


COPY finance-0.0.1-SNAPSHOT.jar /app/finance-0.0.1-SNAPSHOT.jar


EXPOSE 8080

# Run the Spring Boot app
ENTRYPOINT ["java", "-jar", "finance-0.0.1-SNAPSHOT.jar"]