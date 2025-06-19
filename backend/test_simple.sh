#!/bin/bash

echo "=== Teste Simples de POST ==="
echo "1. Testando POST direto no ms-voo..."
curl -X POST http://localhost:8083/flights \
  -H "Content-Type: application/json" \
  -d '{"code":"TEST123","originCodigo":"GRU","destinationCodigo":"GIG","departureTime":"2025-08-01T10:00:00","price":1000.00,"seatCount":150}' \
  -w "\nHTTP_STATUS: %{http_code}\n"

echo -e "\n2. Testando POST via API Gateway..."
curl -X POST http://localhost:8000/api/flights \
  -H "Content-Type: application/json" \
  -d '{"code":"TEST456","originCodigo":"GRU","destinationCodigo":"GIG","departureTime":"2025-08-01T10:00:00","price":1000.00,"seatCount":150}' \
  -w "\nHTTP_STATUS: %{http_code}\n"

echo -e "\n3. Testando GET via API Gateway..."
curl -X GET http://localhost:8000/api/flights \
  -w "\nHTTP_STATUS: %{http_code}\n"

echo "=== Teste Simples de Status ==="

echo -e "\n1. Testando PUT de status no voo ID 8 (recém-criado)..."
curl -X PUT http://localhost:8000/api/flights/8/status \
  -H "Content-Type: text/plain" \
  --data "CANCELADO" \
  -w "\nHTTP_STATUS: %{http_code}\n"

echo -e "\n2. Testando PUT de status no voo ID 5 (já cancelado)..."
curl -X PUT http://localhost:8000/api/flights/5/status \
  -H "Content-Type: text/plain" \
  --data "CONFIRMADO" \
  -w "\nHTTP_STATUS: %{http_code}\n"

echo -e "\n3. Testando POST de reserva..."
curl -X POST http://localhost:8000/api/reservations \
  -H "Content-Type: application/json" \
  -d '{"clientId":1,"flightCode":"JJ9999"}' \
  -w "\nHTTP_STATUS: %{http_code}\n" 