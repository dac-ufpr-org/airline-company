#!/bin/bash

echo "Parando containers..."
docker-compose down

echo "Limpando imagens antigas dos microsserviços..."
for image in airline-api-gateway airline-ms-auth airline-ms-cliente; do
  if docker image inspect $image > /dev/null 2>&1; then
    docker rmi $image
  else
    echo "Imagem $image não encontrada, pulando..."
  fi
done

echo "Compilando todos os módulos Maven (multi-módulo)..."
mvn clean package -DskipTests

echo "Construindo imagens com docker-compose..."
docker-compose build --no-cache

echo "Subindo os serviços em segundo plano..."
docker-compose up -d

echo "Ambiente iniciado com sucesso."
