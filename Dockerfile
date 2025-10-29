# Use lightweight OpenJDK image
FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app
COPY . .

# Build the app
RUN ./mvnw -B -DskipTests package

# Run Spring Boot
CMD ["java", "-jar", "target/*.jar", "--spring.profiles.active=prod"]
