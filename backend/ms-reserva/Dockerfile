# Etapa de build
FROM eclipse-temurin:17-jdk-alpine AS build
WORKDIR /app

# Copia toda a pasta backend para o contexto de build
COPY . .

# Compila o projeto Maven
RUN chmod +x ms-reserva/mvnw
RUN cd ms-reserva && ./mvnw clean package -DskipTests

# Etapa de runtime
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copie o .jar do build
COPY --from=build /app/ms-reserva/target/*.jar app.jar

# Expoe a porta padrão
EXPOSE 8080

# Comando de entrada
ENTRYPOINT ["java", "-jar", "app.jar"]
