# Etapa de build
FROM eclipse-temurin:17-jdk-alpine AS build
WORKDIR /app

# Copia o código-fonte e o wrapper do Maven
COPY . . 

# Compila o projeto Maven
RUN chmod +x mvnw
RUN ./mvnw clean package -DskipTests

# Etapa de runtime
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copie o .jar do build
COPY --from=build /app/target/*.jar app.jar

# Expoe a porta padrão
EXPOSE 8080

# Comando de entrada
ENTRYPOINT ["java", "-jar", "app.jar"]
