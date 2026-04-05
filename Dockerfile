FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY target/aws-devops-demo-1.0.0.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]
