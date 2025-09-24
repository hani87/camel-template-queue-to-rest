# Use Java 23 JDK base image
FROM eclipse-temurin:23-jdk

# Set working directory inside container
WORKDIR /app

# Copy the built JAR from target folder
COPY target/camel-template-queue-to-rest-*.jar app.jar

# Expose port (if your Camel app uses HTTP or REST)
EXPOSE 8080

# Run the application
ENTRYPOINT ["java","-jar","app.jar"]
