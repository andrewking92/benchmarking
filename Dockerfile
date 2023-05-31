# Use an appropriate base image with Maven and Java 11
FROM maven:3.8.4-openjdk-11 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the Maven project file to the container
COPY pom.xml .

# Copy the source code to the container
COPY src ./src

# Build the application with Maven, including the specified build arguments
ARG MONGODB_URI
ARG MONGODB_DATABASE
ARG MONGODB_COLLECTION
RUN mvn clean package exec:java -Dexec.mainClass="com.benchmarking.insert.BulkInsertThreaded" -Dmongodb.uri=$MONGODB_URI -Dmongodb.database=$MONGODB_DATABASE -Dmongodb.collection=$MONGODB_COLLECTION

# Create a new stage with a minimal base image
FROM adoptopenjdk:11-jre-hotspot

# Set the working directory inside the container
WORKDIR /app

# Copy the compiled Java application JAR file from the build stage to the current directory
COPY --from=build /app/target/your-application.jar .

# Set the entrypoint command to run your Java application
ENTRYPOINT ["java", "-jar", "your-application.jar"]
