FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY target/pokemon-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

CMD ["sh", "-c", "java -jar app.jar --server.port=$PORT"]
