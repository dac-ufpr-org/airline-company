#!/bin/bash

echo "=== Teste Rápido de Endpoints ==="

echo -e "\n1. Testando GET /flights..."
curl -s -X GET http://localhost:8000/api/flights -w "\nHTTP_STATUS: %{http_code}\n"

echo -e "\n2. Testando POST /flights..."
curl -s -X POST http://localhost:8000/api/flights \
  -H "Content-Type: application/json" \
  -d '{"code":"QUICK123","originCodigo":"GRU","destinationCodigo":"GIG","departureTime":"2025-08-01T10:00:00","price":1000.00,"seatCount":150}' \
  -w "\nHTTP_STATUS: %{http_code}\n"

echo -e "\n3. Testando POST /reservations..."
curl -s -X POST http://localhost:8000/api/reservations \
  -H "Content-Type: application/json" \
  -d '{"clientId":1,"flightCode":"QUICK123"}' \
  -w "\nHTTP_STATUS: %{http_code}\n"

echo -e "\n=== Teste Concluído ===" 