# Use Eclipse Temurin JDK 17 (same as local setup)
FROM eclipse-temurin:17-jdk

# Set working directory inside container
WORKDIR /app

# Copy the built JAR from target folder
COPY target/contact-management-1.0.jar app.jar

# Run the Contact Management application
CMD ["java", "-jar", "app.jar"]
