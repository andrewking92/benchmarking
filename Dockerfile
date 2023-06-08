# Use an appropriate base image with Maven and Java 11
FROM maven:3.8.4-openjdk-11 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the Maven project file to the container
COPY pom.xml .

# Copy the source code to the container
COPY src ./src

# Build the application with Maven, including the specified build arguments
RUN mvn clean package

# Create a new stage with a minimal base image
FROM adoptopenjdk:11-jre-hotspot

# Set the working directory inside the container
WORKDIR /app

# Copy the compiled Java application JAR file from the build stage to the current directory
COPY --from=build /app/target/benchmarking-1.0-SNAPSHOT-jar-with-dependencies.jar .

# Set the entrypoint command to run your Java application
ENTRYPOINT ["java", "-Dmongodb.uri=mongodb://localhost:27017", "-Dmongodb.database=test" , "-jar", "benchmarking-1.0-SNAPSHOT-jar-with-dependencies.jar", "util", "ping", "1000"]
