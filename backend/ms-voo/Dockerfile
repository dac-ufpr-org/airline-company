# Etapa de build
FROM eclipse-temurin:17-jdk-alpine AS build
WORKDIR /app

# Copia toda a pasta backend para o contexto de build
COPY . .

# Instala dos2unix e converte o mvnw para Unix
RUN apk add --no-cache dos2unix && dos2unix mvnw && chmod +x mvnw

# Compila todos os módulos Maven para garantir dependências locais
RUN ./mvnw clean package -DskipTests

# Etapa de runtime
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copie o .jar do build
COPY --from=build /app/ms-voo/target/*.jar app.jar

# Expoe a porta padrão do serviço de voo
EXPOSE 8080

# Comando de entrada
ENTRYPOINT ["java", "-jar", "app.jar"]
