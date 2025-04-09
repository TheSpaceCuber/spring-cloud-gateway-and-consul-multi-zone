# Use the official OpenJDK 17 image as the base
FROM openjdk:17-jdk

# Copy the Maven build files and source code
COPY target/microservice-a-0.0.1-SNAPSHOT.jar /app.jar

# Run the Spring Boot app when the container starts
ENTRYPOINT ["java", "-jar", "app.jar"]