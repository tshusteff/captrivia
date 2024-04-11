# TODO: Consider making this Dockerfile nicer or adding a multistage build.

# Builder stage
FROM openjdk:21-jdk-bookworm
WORKDIR /app

# Install maven
RUN apt-get update && apt-get install -y maven

# Copy source
COPY . .

# Build the server
WORKDIR captrivia
RUN mvn package

EXPOSE 8080

# Start up the server
CMD ["java", "-jar", "./target/captrivia-1.0-SNAPSHOT.jar", "server", "captrivia.yml"]
