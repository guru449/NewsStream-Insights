# Use Java 18 JDK for building
FROM openjdk:18-oracle as build
WORKDIR /app
COPY . /app
# Run Gradle build
RUN ./gradlew clean build -x test  # Assumes you have a gradlew in your project

# Use Java 18 JRE for running
FROM openjdk:18-oracle
WORKDIR /app
# Copy the built JAR from the build/libs directory
COPY --from=build /app/build/libs/*.jar app.jar
CMD ["java", "-jar", "app.jar"]

