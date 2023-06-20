FROM maven:3.8.1-openjdk-17-slim


WORKDIR /app
COPY . /app

RUN mvn clean package -DskipTests

EXPOSE 8080

# Define the command to run your application
CMD ["java", "-jar", "target/Mini-property-management.jar"]
