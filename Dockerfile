# ---- Build stage ----
FROM maven:3.9.4-openjdk-17-slim AS build
WORKDIR /home/app

# copy only necessary files first for better caching
COPY pom.xml mvnw ./
COPY .mvn .mvn
RUN mvn -B -f pom.xml -q dependency:go-offline

# copy source and build
COPY src ./src
RUN mvn -B -DskipTests package

# ---- Runtime stage ----
FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app

# copy jar produced in build stage (match artifact name if needed)
COPY --from=build /home/app/target/*.jar app.jar

# expose port (optional, Render sets it via env)
EXPOSE 8080

# run with prod profile by default (can be overridden by env var)
ENTRYPOINT ["sh", "-c", "java -jar /app/app.jar --spring.profiles.active=${SPRING_PROFILES_ACTIVE:-prod}"]
