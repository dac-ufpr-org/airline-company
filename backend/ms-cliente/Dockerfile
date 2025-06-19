# Etapa de build
FROM eclipse-temurin:17-jdk-alpine AS build
WORKDIR /app

# Copia toda a pasta backend para o contexto de build
COPY . .

# Instala dos2unix para garantir que o mvnw tenha finais de linha corretos
RUN apk add --no-cache dos2unix \
    && dos2unix mvnw \
    && chmod +x mvnw

# Compila o projeto Maven multi-módulo a partir da raiz
RUN ./mvnw clean package -DskipTests

# Etapa de runtime
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copie o .jar do build
COPY --from=build /app/ms-cliente/target/*.jar app.jar

# Expoe a porta padrão
EXPOSE 8080

# Comando de entrada
ENTRYPOINT ["java", "-jar", "app.jar"]
