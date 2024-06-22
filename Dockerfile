# Estágio de construção
FROM gradle:jdk17 as build
WORKDIR /app
COPY . .
RUN gradle clean build -x test

# Estágio de execução
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
