FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app
COPY build/libs/sports-facility.jar app.jar
EXPOSE 8086
ENTRYPOINT ["java", "-jar", "app.jar"]
